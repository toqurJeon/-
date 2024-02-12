from bs4 import BeautifulSoup
from selenium import webdriver
import chromedriver_autoinstaller


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

    # Selenium을 이용하여 웹 페이지를 엽니다.
    url = "https://translate.google.co.kr/?sl=auto&tl=en&text=" + caption + "?&op=translate"
    driver = webdriver.Chrome()
    driver.get(url)

    # 페이지가 로딩될 때까지 기다립니다.
    driver.implicitly_wait(5)

    # 페이지 소스를 가져와 BeautifulSoup으로 파싱합니다.
    soup = BeautifulSoup(driver.page_source, 'lxml')

    # 카테고리 정보를 찾습니다.
    translated = soup.select('span.ryNqvb')
    print(translated)
    # category = category_element[3].text if category_element else 'Not Found'
    for i in translated:
        print(i.text)
    # print(f"번역 결과: {category}")

    # 브라우저를 닫습니다.
    driver.quit()


if __name__ == '__main__':
    # crawl_playstore('com.wooribank.smart.npib')
    crawl_translator('오우오우')
