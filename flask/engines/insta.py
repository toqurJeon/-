def calc(info):
    for i in info:
        if i['media_type'] == 'CAROUSEL_ALBUM':
            continue
        elif i['media_type'] == 'IMAGE':
            url = i['media_url']
            # url vision process
        else:
            continue

        text = i['caption']
        # text nlp process

        # 뭔가 둘 사이의 관계성 나타내는 코드

    res = {"vision val: ": 0.1235,
           "nlp val": 0.7245,
           "total: ": 0.8423}
    return res
