import requests


def calc(input, output):
    if input == [] or input is None:
        return None

    access_token = input["access_token"]
    try:
        # 액세스 토큰으로 사용자 피드 목록 받아오기
        like_request = requests.get('https://youtube.googleapis.com/youtube/v3/videos?part=snippet&myRating=like'
                                    '&key=AIzaSyDXA4Zm5Axq75Qw_M31PN1S4rUBGJIRSxc',
                                    headers={'Authorization': 'Bearer ' + access_token})

        if like_request.status_code == 200:
            like_list = like_request.json()['snippet']

        categories = {}
        # for문 돌면서 피드 정보 가져와서 저장
        for like in like_list:
            print(like)
            # 좋아요 누른 동영상 코드 따기

            category_code = like['snippet']['code']

            if category_code not in categories:
                categories[category_code] = 1
            else:
                categories[category_code] += 1

        sorted_categories = dict(sorted(categories.items(), key=lambda item: item[1], reverse=True))

        for category_code, value in sorted_categories.items():
            output['interests'].append({'interestName': category_code,
                                        'interestPercent': round(10000 * value / len(like_list))})

    except Exception as e:
        return None
