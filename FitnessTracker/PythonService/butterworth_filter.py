from scipy.signal import butter, filtfilt

def lowpass_filter(original_df,cutoff_freq, sampling_freq, order, col):
    data_table = original_df.copy()
    nyquist_freq = 0.5 * sampling_freq
  
    b, a = butter(order, cutoff_freq / nyquist_freq, btype='low', output='ba', analog=False)
    data_table[col + "_lowpass"] = filtfilt(b,a,data_table[col])
    return data_table