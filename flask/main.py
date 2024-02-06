from flask import Flask, request, jsonify, render_template
import requests

from engines import gps, insta, call

app = Flask(__name__)


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


@app.route('/receive_and_send', methods=['POST'])
def receive_and_send():
    try:
        # JSON 데이터 받아오기
        json_data = request.get_json()

        # JSON 데이터의 각 요소에 접근
        param1 = json_data.get('param1', -0.0001)
        param2 = json_data.get('param2', -0.0001)
        param3 = json_data.get('param3', -0.0001)

        # 받은 데이터 출력
        # 여기서 데이터 가공

        # print("Received JSON: Name={}, Age={}".format(name, age))

        component1 = round(param1 * 10000)
        component2 = round((param1 + param2) * 5000)
        component3 = round(param2 * 10000)
        component4 = round(param3 * 10000)
        component5 = round(param1 * 10000)
        component6 = round(param2 * 10000)
        component7 = round(param3 * 10000)
        component8 = round(param1 * 10000)

        # /send_json 엔드포인트로 POST 요청 보내기
        # 이 url에 springboot url 들어갈 예정
        send_response = (
            requests.post('http://i10a308.p.ssafy.io:8080/member',
                          json={'component1': component1,
                                'component2': component2,
                                'component3': component3,
                                'component4': component4,
                                'component5': component5,
                                'component6': component6,
                                'component7': component7,
                                'component8': component8}))

        # /send_json 엔드포인트에서 받은 응답 출력
        print("Response from /send_json:", send_response.json())

        return jsonify({'status': 'success', 'message': 'JSON processed and sent successfully'})
    except Exception as e:
        return jsonify({'status': 'error', 'message': str(e)})


# 실제로는 spring이 대체할 예정이나, 테스트를 위해 작성
@app.route('/send_json', methods=['POST'])
def send_json():
    try:
        # JSON 데이터 받아오기
        json_data = request.get_json()

        # 받은 데이터 출력
        print("Received JSON for /send_json:", json_data)

        # 처리 후 응답 반환
        return jsonify({'status': 'success', 'message': 'JSON received and processed successfully'})
    except Exception as e:
        return jsonify({'status': 'error', 'message': str(e)})


if __name__ == '__main__':
    app.run(host='0.0.0.0', debug=True)
