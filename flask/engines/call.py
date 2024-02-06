import matplotlib.pyplot as plt
from datetime import datetime

plt.rcParams['font.family'] = 'Malgun Gothic'
plt.rcParams['axes.unicode_minus'] = False


def calc(json_arr):
    phone_numbers = []
    owner_name = []
    call_start_times = []
    call_durations = []
    for json in json_arr:
        phone_numbers.append(json['number'])
        owner_name.append(json['name'])
        call_start_times.append(datetime.utcfromtimestamp(int(json['date']) // 1000).strftime('%H:%M:%S'))
        call_durations.append(json['duration'])

    # 통화 시작 시간에 따른 통화 시간 시각화 (날짜 제외, 시간만 표시)
    for i, phone_number in enumerate(phone_numbers):
        plt.scatter(call_start_times[i], call_durations[i], label=phone_number)

    plt.xlabel('통화 시작 시간 (시간)')
    plt.ylabel('통화 시간 (초)')
    plt.title('전화번호별 통화 시작 시간에 따른 통화 시간 (시간만 표시)')
    plt.legend()
    plt.xticks(rotation=45)
    plt.show()

    result = {'key': 'value'}
    return result
