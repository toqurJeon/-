import requests
from selenium import webdriver
from selenium.webdriver.common.by import By
import time
import nltk

nltk.download('punkt')
from textblob import TextBlob


def calc_pos(blob):
    sentiment_avg = 0
    for sentence in blob.sentences:
        # print(sentence.sentiment.polarity, sentence.sentiment.subjectivity, sentence)
        sentiment_avg += sentence.sentiment.polarity
    sentiment_avg = sentiment_avg / len(blob.sentences)

    return round((sentiment_avg + 1) * 5000)


def calc_emp(blob):
    sentiment_avg = 0
    for sentence in blob.sentences:
        # print(sentence.sentiment.polarity, sentence.sentiment.subjectivity, sentence)
        sentiment_avg += sentence.sentiment.subjectivity
    sentiment_avg = sentiment_avg / len(blob.sentences)

    return round(sentiment_avg * 10000)


def calc(input, output):
    if input == '':
        return None

    access_token = input
    try:
        # 액세스 토큰으로 사용자 피드 목록 받아오기
        feed_request = requests.get('https://graph.instagram.com/me/media?fields=id,caption&access_token='
                                    + access_token)

        feed_list = []
        if feed_request.status_code == 200:
            feed_list = feed_request.json()['data']

        # for문 돌면서 피드 정보 가져와서 저장
        captions = ''
        for feed in feed_list:
            captions += feed['caption'] + '.\n'

        # ChromeDriver를 자동으로 설치합니다.
        # chromedriver_autoinstaller.install()

        # Selenium을 이용하여 웹 페이지를  엽니다.
        url = "https://papago.naver.com/"
        options = webdriver.ChromeOptions()
        options.add_argument('--headless')
        options.add_argument('--no-sandbox')
        options.add_argument('--disable-dev-shm-usage')
        driver = webdriver.Chrome(options=options)
        #driver = webdriver.Chrome()
        driver.get(url)

        # 페이지가 로딩될 때까지 기다립니다.
        driver.implicitly_wait(5)

        source_textbox = driver.find_element(By.ID, "txtSource")
        source_textbox.clear()
        source_textbox.send_keys(captions)

        # 번역 결과 기다림 (일부 서버 응답에 시간이 걸릴 수 있으므로 적절한 대기 시간을 설정)
        time.sleep(5)

        # 번역된 텍스트 가져오기
        target_textbox = driver.find_element(By.ID, "txtTarget")
        translated_text = target_textbox.text

        # # 결과 출력
        # print(f"Source Text: {caption}")
        # print(f"Translated Text: {translated_text}")

        # 브라우저 종료
        driver.quit()

        blob = TextBlob(translated_text)

        output['characteristic']['positivity'] = calc_pos(blob)
        output['characteristic']['empathy'] = calc_emp(blob)

    except Exception as e:
        return None
