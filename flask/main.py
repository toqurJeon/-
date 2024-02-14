from flask import Flask, request, jsonify, render_template
from flask_cors import CORS
import requests

from domain import apps, call, images, insta, youtube

app = Flask(__name__)
CORS(app)

params = {'characteristic': {'sociability': 0, 'positivity': 0, 'activity': 0, 'communion': 0,
                             'altruism': 0, 'empathy': 0, 'humor': 0, 'generous': 0}, 'interests': []}


@app.route('/moiroom/privacy')
def privacy():
    # Flask의 render_template 함수를 사용하여 HTML 파일을 렌더링합니다.
    return render_template('index.html')


@app.route('/init', methods=['POST'])
def user_init():
    try:
        # JSON 데이터 받아오기
        json_data = request.get_json()
        access_token = 'Bearer ' + json_data['accessToken']

        images.calc(json_data['images'], params)
        call.calc(json_data['calls'], params)
        # apps.calc(json_data['apps'], params)
        insta.calc(json_data['insta'], params)
        # youtube.calc(json_data['youtube'], params)
        params['characteristic']['sleepAt'] = "22:00"
        params['characteristic']['wakeUpAt'] = "06:30"

        print(params)

        send_response = requests.post('https://moiroom.n-e.kr/member/characteristic',
                                      headers={'Authorization': access_token},
                                      json=params)
        return send_response.json()

    except Exception as e:
        return jsonify({'status': 'error', 'message': str(e)})


@app.route('/match', methods=['POST'])
def match_users():
    try:
        access_token = 'Bearer ' + request.get_json()['accessToken']
        users_request = requests.get('https://moiroom.n-e.kr/matching/info', headers={'Authorization': access_token})
        if users_request.status_code != 200:
            return jsonify({'status': 'error', 'message': 'not 200 in users info'})
        print(users_request.json())
        users_info = users_request.json()['data']

        results = []
        for users in users_info['memberTwos']:
            percentage = 10000
            min_gap = [10000, '']
            for character, val in users['characteristic'].items():
                if character == 'sleepAt' or character == 'wakeUpAt':
                    continue
                gap = abs(users_info['memberOne']['characteristic'][character] - val)
                percentage -= gap
                if gap < min_gap[0]:
                    min_gap[0] = gap
                    min_gap[1] = character + ' fits well'
            # print({'rate': percentage, 'rateIntroduction': min_gap[1]})
            results.append({'memberTwoId': users['memberId'], 'rate': percentage, 'rateIntroduction': min_gap[1]})

        send_response = requests.post('https://moiroom.n-e.kr/matching/result',
                                      headers={'Authorization': access_token},
                                      json={'matchingResults': results})

        return send_response.json()

    except Exception as e:
        return jsonify({'status': 'error', 'message': str(e)})


if __name__ == '__main__':
    app.run(host='0.0.0.0', debug=True)
