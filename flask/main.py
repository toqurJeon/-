from flask import Flask, request, jsonify, render_template
from flask_cors import CORS
import requests

from engines import gps, insta, call

app = Flask(__name__)
CORS(app)

@app.route('/moiroom/test')
def test():
    # Flask의 render_template 함수를 사용하여 HTML 파일을 렌더링합니다.
    return render_template('test.html')


@app.route('/moiroom/privacy')
def privacy():
    # Flask의 render_template 함수를 사용하여 HTML 파일을 렌더링합니다.
    return render_template('index.html')


@app.route('/gps', methods=['POST'])
def gps_func():
    try:
        # JSON 데이터 받아오기
        json_data = request.get_json()

        return jsonify(gps.calc(json_data["data"]))

    except Exception as e:
        return jsonify({'status': 'error', 'message': str(e)})


@app.route('/insta', methods=['POST'])
def insta_func():
    try:
        # JSON 데이터 받아오기
        json_data = request.get_json()

        access_token = json_data["access_token"]

        # 액세스 토큰으로 사용자 피드 목록 받아오기
        feed_request = requests.get('https://graph.instagram.com/me/media?fields=id,caption&access_token='
                                    + access_token)

        if feed_request.status_code != 200:
            return jsonify({'status': 'error', 'message': 'not 200 in feed list'})
        feed_list = feed_request.json()['data']

        # for문 돌면서 피드 정보 가져와서 저장
        info = []
        for feed in feed_list:
            print(feed)
            content_request = requests.get(
                'https://graph.instagram.com/' + feed['id'] + '?fields=media_type,media_url,'
                                                              'caption&access_token=' + access_token)
            if content_request.status_code == 200:
                info.append(content_request.json())
            else:
                return jsonify({'status': 'error', 'message': 'not 200 in feed list'})

        return jsonify(insta.calc(info))

    except Exception as e:
        return jsonify({'status': 'error', 'message': str(e)})


@app.route('/call', methods=['POST'])
def call_func():
    try:
        # JSON 데이터 받아오기
        json_data = request.get_json()

        return jsonify(call.calc(json_data["calls"]))

    except Exception as e:
        return jsonify({'status': 'error', 'message': str(e)})


def sociability(json_data):
    return 5000


def positivity(json_data):
    return 5000


def activity(json_data):
    if not json_data['images']:
        return 0
    else:
        return gps.calc(json_data['images'])


def communion(json_data):
    return 5000


def altruism(json_data):
    return 5000


def empathy(json_data):
    if not json_data['calls']:
        return 0
    else:
        return call.calc(json_data["calls"])


def humor(json_data):
    return 5000


def generous(json_data):
    return 5000


func_list = [sociability, positivity, activity, communion, altruism, empathy, humor, generous]
match_introduction = {}
for f in func_list:
    match_introduction[f.__name__] = f.__name__ + ' fits well'


@app.route('/init', methods=['POST'])
def user_init():
    try:
        # JSON 데이터 받아오기
        json_data = request.get_json()
        params = {}
        for function in func_list:
            params[function.__name__] = function(json_data)
        params['sleepAt'] = None
        params['wakeUpAt'] = None
        params['interest'] = None

        # print(params)
        send_response = requests.post('https://moiroom.n-e.kr/member/characteristic', json=params)
        return send_response.json()

    except Exception as e:
        return jsonify({'status': 'error', 'message': str(e)})


# @app.route('/match', methods=['POST'])
@app.route('/match', methods=['POST'])
def match_users():
    try:
        users_request = requests.get('https://moiroom.n-e.kr/matching/info')
        if users_request.status_code != 200:
            return jsonify({'status': 'error', 'message': 'not 200 in users info'})
        users_info = users_request.json()['data']
        # users_info = request.get_json()['data']

        for users in users_info['memberTwoList']:
            percentage = 10000
            min_gap = [10000, '']
            for func in func_list:
                gap = abs(users_info['memberOne'][func.__name__] - users[func.__name__])
                percentage -= gap
                if gap < min_gap[0]:
                    min_gap[0] = gap
                    min_gap[1] = match_introduction[func.__name__]
            # print({'rate': percentage, 'rateIntroduction': min_gap[1]})
            send_response = requests.post('https://moiroom.n-e.kr/matching/result/' + users['memberId'],
                                          json={'rate': percentage, 'rateIntroduction': min_gap[1]})
            # if error occurs, return error

        return jsonify({'status': 'success', 'message': 'JSON processed and sent successfully'})

    except Exception as e:
        return jsonify({'status': 'error', 'message': str(e)})


if __name__ == '__main__':
    app.run(host='0.0.0.0', debug=True)
