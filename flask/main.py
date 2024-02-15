from flask import Flask, request, jsonify, render_template
from flask_cors import CORS
import requests

from domain import apps, call, images, insta, youtube

app = Flask(__name__)
CORS(app)

params = {'characteristic': {'sociability': 5678, 'positivity': 7896, 'activity': 1874, 'communion': 3814,
                             'altruism': 1234, 'empathy': 4183, 'humor': 8423, 'generous': 4538}, 'interests': []}


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
        # insta.calc(json_data['insta'], params)
        call.calc(json_data['calls'], params)
        params['interests'] = youtube.calc(json_data['youtube'])
        # apps.calc(json_data['apps'], params)


        print(params)

        send_response = requests.post('https://moiroom.n-e.kr/member/characteristic',
                                      headers={'Authorization': access_token},
                                      json=params)
        return send_response.json()

    except Exception as e:
        return jsonify({'status': 'error', 'message': str(e)})


introduct_message = {'sociability': '함께 많은 활동을 즐기세요!',
                     'positivity': '긍정적인 에너지가 넘칠거예요!',
                     'activity': '활발함이 배가 될 것 같아요!',
                     'communion': '사소하다 생각 말고, 뭐든 같이 이야기해요.',
                     'altruism': '서로를 배려하는 모습이 아름다워요!',
                     'empathy': '들어줄게요, 말해봐요.',
                     'humor': '우리 뭔가 잘 맞는 것 같지 않아요?',
                     'generous': '사소한 것들은 넘겨버려요!',
                     'interests': '여가 생활도 서로 같이 즐겨요!'}

@app.route('/match', methods=['POST'])
def match_users():
    try:
        access_token = 'Bearer ' + request.get_json()['accessToken']
        users_request = requests.get('https://moiroom.n-e.kr/matching/info', headers={'Authorization': access_token})
        if users_request.status_code != 200:
            return jsonify({'status': 'error', 'message': 'not 200 in users info, ' + str(users_request.status_code)})
        print(users_request.json())
        users_info = users_request.json()['data']

        results = []
        for users in users_info['memberTwos']:
            percentage = 80000
            min_gap = [10000, '']
            for character, val in users['characteristic'].items():
                if character == 'sleepAt' or character == 'wakeUpAt':
                    continue
                gap = abs(users_info['memberOne']['characteristic'][character] - val)
                percentage -= gap
                if gap < min_gap[0]:
                    min_gap[0] = gap
                    min_gap[1] = introduct_message[character]
            # print({'rate': percentage, 'rateIntroduction': min_gap[1]})
            results.append({'memberTwoId': users['memberId'], 'rate': round(percentage/8), 'rateIntroduction': min_gap[1]})

        send_response = requests.post('https://moiroom.n-e.kr/matching/result',
                                      headers={'Authorization': access_token},
                                      json={'matchingResults': results})

        return send_response.json()

    except Exception as e:
        return jsonify({'status': 'error', 'message': str(e)})


if __name__ == '__main__':
    app.run(host='0.0.0.0', debug=True)
