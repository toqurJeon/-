from datetime import datetime


def calc_emp(data_by_phone_number):
    total_duration = 0
    parents_total_duration = 0
    for phone_number, data in data_by_phone_number.items():
        sum_of_duration = sum([data['durations'][i] for i in range(len(data['durations']))])
        total_duration += sum_of_duration
        if '엄마' in data['owner_name'] or '아빠' in data['owner_name']:
            parents_total_duration += sum_of_duration
    return round((parents_total_duration / total_duration) * 10000)


def calc_com(data_by_phone_number):
    values = []

    for key, inner_dict in data_by_phone_number.items():
        if 'durations' in inner_dict:
            values.append(sum(inner_dict['durations']))

    if values:
        average = sum(values) / (len(values) * len(values))
    else:
        average = 2
    return 10000 - round(10000 / average)


def calc_gen(data_by_phone_number):
    return 5000


def calc(input, output):
    if input == [] or input is None:
        return None
    phone_numbers = []
    owner_names = []
    call_start_times = []
    call_durations = []
    for i in input:
        phone_numbers.append(i['number'])
        if i['name'] != 'null':
            owner_names.append(i['name'])
        else:
            owner_names.append('unregistered')
        call_start_times.append(datetime.utcfromtimestamp(int(i['date']) // 1000).strftime('%H:%M:%S'))
        call_durations.append(i['duration'])

    # 각 전화번호에 대한 통화 시작 시간과 통화 시간을 저장할 딕셔너리
    data_by_phone_number = {}

    # 데이터 정리
    for phone_number, start_time, duration, owner_name in zip(phone_numbers, call_start_times, call_durations,
                                                              owner_names):
        if phone_number not in data_by_phone_number:
            data_by_phone_number[phone_number] = {'times': [], 'durations': [], 'owner_name': owner_name}
        data_by_phone_number[phone_number]['times'].append(start_time)
        data_by_phone_number[phone_number]['durations'].append(duration)

    output['characteristic']['communion'] = calc_com(data_by_phone_number)
    output['characteristic']['generous'] = calc_gen(data_by_phone_number)
    output['characteristic']['empathy'] = calc_emp(data_by_phone_number)
