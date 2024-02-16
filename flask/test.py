from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.webdriver.common.by import By
import time
import chromedriver_autoinstaller
import nltk

nltk.download('punkt')
from textblob import TextBlob


def crawl_playstore(package_name):
    # ChromeDriver를 자동으로 설치합니다.
    chromedriver_autoinstaller.install()

    # Selenium을 이용하여 웹 페이지를 엽니다.
    url = "https://play.google.com/store/apps/details?id=" + package_name
    driver = webdriver.Chrome()
    driver.get(url)

    # 페이지가 로딩될 때까지 기다립니다.
    driver.implicitly_wait(5)

    # 페이지 소스를 가져와 BeautifulSoup으로 파싱합니다.
    soup = BeautifulSoup(driver.page_source, 'lxml')

    # 카테고리 정보를 찾습니다.
    category_element = soup.select('span.VfPpkd-vQzf8d')
    category = category_element[3].text if category_element else 'Not Found'

    print(f"어플 카테고리: {category}")

    # 브라우저를 닫습니다.
    driver.quit()


def crawl_translator(caption):
    # ChromeDriver를 자동으로 설치합니다.
    chromedriver_autoinstaller.install()

    # Selenium을 이용하여 웹 페이지를  엽니다.
    url = "https://papago.naver.com/"
    driver = webdriver.Chrome()
    driver.get(url)

    # 페이지가 로딩될 때까지 기다립니다.
    driver.implicitly_wait(5)

    source_textbox = driver.find_element(By.ID, "txtSource")
    source_textbox.clear()
    source_textbox.send_keys(caption)

    # 번역 결과 기다림 (일부 서버 응답에 시간이 걸릴 수 있으므로 적절한 대기 시간을 설정)
    time.sleep(2)

    # 번역된 텍스트 가져오기
    target_textbox = driver.find_element(By.ID, "txtTarget")
    translated_text = target_textbox.text

    # 결과 출력
    print(f"Source Text: {caption}")
    print(f"Translated Text: {translated_text}")

    # 브라우저 종료
    driver.quit()

    blob = TextBlob(translated_text)
    print(blob.sentiment.polarity, blob.sentiment.subjectivity, blob)


if __name__ == '__main__':
    # crawl_playstore('com.wooribank.smart.npib')
    crawl_translator('술마심 기분좋음')
