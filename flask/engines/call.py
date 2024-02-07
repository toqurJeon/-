import matplotlib.pyplot as plt
from datetime import datetime

plt.rcParams['font.family'] = 'Malgun Gothic'
plt.rcParams['axes.unicode_minus'] = False


def calc(json_arr):
    phone_numbers = []
    owner_names = []
    call_start_times = []
    call_durations = []
    for json in json_arr:
        phone_numbers.append(json['number'])
        if json['name'] != 'null':
            owner_names.append(json['name'])
        else:
            owner_names.append('')
        call_start_times.append(datetime.utcfromtimestamp(int(json['date']) // 1000).strftime('%H:%M:%S'))
        call_durations.append(json['duration'])

    # 각 전화번호에 대한 통화 시작 시간과 통화 시간을 저장할 딕셔너리
    data_by_phone_number = {}

    # 데이터 정리
    for phone_number, start_time, duration, owner_name in zip(phone_numbers, call_start_times, call_durations,
                                                              owner_names):
        if phone_number not in data_by_phone_number:
            data_by_phone_number[phone_number] = {'times': [], 'durations': [], 'owner_name': owner_name}
        data_by_phone_number[phone_number]['times'].append(start_time)
        data_by_phone_number[phone_number]['durations'].append(duration)


    # 통화 시작 시간에 따른 통화 시간 시각화 (날짜 제외, 시간만 표시)
    for phone_number, data in data_by_phone_number.items():
        plt.scatter(data['times'], data['durations'], label=data['owner_name'])

    plt.xlabel('통화 시작 시간 (시간)')
    plt.ylabel('통화 시간 (초)')
    plt.title('전화번호별 통화 시작 시간에 따른 통화 시간 (시간만 표시)')
    plt.legend()
    plt.xticks(rotation=45)
    plt.show()

    parents_total_duration = 0
    for phone_number, data in data_by_phone_number.items():
        if '엄마' in data['owner_name'] or '아빠' in data['owner_name']:
            parents_total_duration += sum([data['durations'][i] for i in range(len(data['durations']))])

    data_by_phone_number['parents_total_duration'] = parents_total_duration
    return data_by_phone_number
