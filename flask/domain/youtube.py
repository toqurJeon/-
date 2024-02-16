import requests


def code_converter(category_code):
    if category_code == 1:
        return "Film & Animation"
    elif category_code == 2:
        return "Autos & Vehicles"
    elif category_code == 10:
        return "Music"
    elif category_code == 15:
        return "Pets & Animals"
    elif category_code == 17:
        return "Sports"
    elif category_code == 19:
        return "Travel & Events"
    elif category_code == 20:
        return "Gaming"
    elif category_code == 21:
        return "Videoblogging"
    elif category_code == 22:
        return "People & Blogs"
    elif category_code == 23:
        return "Comedy"
    elif category_code == 24:
        return "Entertainment"
    elif category_code == 25:
        return "News & Politics"
    elif category_code == 26:
        return "Howto & Style"
    elif category_code == 27:
        return "Education"
    elif category_code == 28:
        return "Science & Technology"
    elif category_code == 29:
        return "Nonprofits & Activism"
    else:
        return "Others"


def calc(input):
    if input is None:
        return None

    access_token = input['access_token']
    try:
        # 액세스 토큰으로 사용자 피드 목록 받아오기
        like_request = requests.get('https://youtube.googleapis.com/youtube/v3/videos?part=snippet&myRating=like'
                                    '&key=AIzaSyDXA4Zm5Axq75Qw_M31PN1S4rUBGJIRSxc',
                                    headers={'Authorization': 'Bearer ' + access_token})

        if like_request.status_code == 200:
            like_list = like_request.json()['items']

        categories = {}
        # for문 돌면서 피드 정보 가져와서 저장
        for like in like_list:
            # 좋아요 누른 동영상 코드 따기

            category_code = like['snippet']['categoryId']

            if category_code not in categories:
                categories[category_code] = 1
            else:
                categories[category_code] += 1

        sorted_categories = dict(sorted(categories.items(), key=lambda item: item[1], reverse=True))

        return_list = []
        for category_code, value in sorted_categories.items():
            return_list.append({'interestName': code_converter(int(category_code)),
                                'interestPercent': round(10000 * value / len(like_list))})
        return return_list

    except Exception as e:
        return None
