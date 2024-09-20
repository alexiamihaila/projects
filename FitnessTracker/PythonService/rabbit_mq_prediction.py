import pika
import joblib
import pandas as pd
from creating_pipeline import RemoveNullsAndSpaces, ConvertToDatetime, LowpassFilter, SumOfSquares, RollingStats, KMeansClustering
import json
import logging

# Configure logging
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')

# Load the model and pipeline
model = joblib.load('trained_model_final.pkl')
pipeline = joblib.load('fitness_pipeline.pkl')

# Define expected columns
expected_columns = [
 'Z_accelerom_temp_mean',
 'Z_accelerom_temp_std',
 'Y_accelerom',
 'X_accelerom',
 'acc_r_temp_std',
 'acc_r',
 'acc_r_temp_mean',
 'Y_accelerom_temp_std',
 'Y_accelerom_temp_mean',
 'Z_accelerom',
 'X_accelerom_temp_std',
 'X_accelerom_temp_mean'
]

def send_prediction_to_rabbitmq(prediction):
    try:
        connection = pika.BlockingConnection(pika.ConnectionParameters(
            host='goose.rmq2.cloudamqp.com',
            port=5672,
            virtual_host='gwqwxywo',
            credentials=pika.PlainCredentials('gwqwxywo', 'ntgFhoV5MGfdg-drZjLtPBHYRnSJvx1C')
        ))
        channel = connection.channel()
        channel.queue_declare(queue='predicted_exercise_queue')

        message = json.dumps({'predicted_exercise': prediction})
        channel.basic_publish(exchange='', routing_key='predicted_exercise_queue', body=message)
        logging.info(f'Sent prediction: {message}')

        connection.close()
    except Exception as e:
        logging.error(f'Error sending prediction to RabbitMQ: {str(e)}')

def process_data(ch, method, properties, body):
    logging.info('Received data')
    try:
        data = json.loads(body)
        df = pd.DataFrame(data)
     
        logging.info(f'Data received: {df.head()}')

        required_columns = {'X_accelerom', 'Y_accelerom', 'Z_accelerom', 'Timestamp'}
        if not required_columns.issubset(df.columns):
            logging.error('Missing required columns')
            return

        df = df.astype({'X_accelerom': 'float64', 'Y_accelerom': 'float64', 'Z_accelerom': 'float64', 'Timestamp': 'str'})
        df['Timestamp'] = pd.to_datetime(df['Timestamp'])
        df['Exercise_Type'] = 'Unknown'

        logging.info(f'Data before pipeline: {df.head()}')

        processed_data = pipeline.transform(df)
        processed_data = processed_data.drop(columns=['Exercise_Type', 'Timestamp'])

        logging.info(f'Processed data columns: {processed_data.columns.tolist()}')

        for column in expected_columns:
            if column not in processed_data.columns:
                processed_data[column] = 0.0 

        # Reorder columns to match the expected format
        processed_data = processed_data[expected_columns]

        logging.info(f'Processed data after reordering: {processed_data.head()}')

        prediction = model.predict(processed_data)
        predicted_exercise = str(prediction[0])
        logging.info(f'Predicted exercise: {predicted_exercise}')
        
        # Send the prediction to the new RabbitMQ queue
        send_prediction_to_rabbitmq(predicted_exercise)
    except Exception as e:
        logging.error(f'Error processing data: {str(e)}')

def consume_from_rabbitmq():
    try:
        connection = pika.BlockingConnection(pika.ConnectionParameters(
            host='goose.rmq2.cloudamqp.com',
            port=5672,
            virtual_host='gwqwxywo',
            credentials=pika.PlainCredentials('gwqwxywo', 'ntgFhoV5MGfdg-drZjLtPBHYRnSJvx1C')
        ))
        channel = connection.channel()
        channel.queue_declare(queue='sensor_data_queue')

        channel.basic_consume(queue='sensor_data_queue', on_message_callback=process_data, auto_ack=True)

        logging.info('Waiting for messages...')
        channel.start_consuming()
    except Exception as e:
        logging.error(f'Failed to connect or consume from RabbitMQ: {str(e)}')

if __name__ == '__main__':
    consume_from_rabbitmq()
