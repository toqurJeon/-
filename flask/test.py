from flask import Flask, redirect, url_for, session, request
from flask_oauthlib.client import OAuth

app = Flask(__name__)
app.secret_key = 'your_secret_key'  # 반드시 안전한 랜덤한 키로 변경해야 합니다.

oauth = OAuth(app)

# Instagram API 설정
instagram = oauth.remote_app(
    'instagram',
    consumer_key='wjswwkd@gmail.com',
    consumer_secret='Lvp2016!',
    request_token_params=None,
    base_url='https://api.instagram.com/v1/',
    request_token_url=None,
    access_token_url='/oauth/access_token',
    authorize_url='https://api.instagram.com/oauth/authorize/',
)


@app.route('/')
def index():
    return 'Welcome to Flask Instagram API Example'


@app.route('/login')
def login():
    return instagram.authorize(callback=url_for('authorized', _external=True))


@app.route('/logout')
def logout():
    session.pop('instagram_token', None)
    return 'Logged out'


@app.route('/login/authorized')
def authorized():
    response = instagram.authorized_response()

    if response is None or response.get('access_token') is None:
        return 'Access denied: reason={} error={}'.format(
            request.args['error_reason'],
            request.args['error_description']
        )

    session['instagram_token'] = (response['access_token'], '')
    user_info = instagram.get('users/self')
    return 'Logged in as {}<br>Access Token: {}'.format(user_info.data['data']['username'],
                                                        session['instagram_token'][0])


@instagram.tokengetter
def get_instagram_oauth_token():
    return session.get('instagram_token')


if __name__ == '__main__':
    app.run(debug=True)
