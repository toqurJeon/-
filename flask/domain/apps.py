from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.webdriver.common.by import By
import chromedriver_autoinstaller

def calc(input, output):
    # ChromeDriver를 자동으로 설치합니다.
    chromedriver_autoinstaller.install()
    driver = webdriver.Chrome()

    categories = {}
    for i in input:
        # Selenium을 이용하여 웹 페이지를 엽니다.
        url = "https://play.google.com/store/apps/details?id=" + i['packageName']
        driver.get(url)

        # 페이지가 로딩될 때까지 기다립니다.
        driver.implicitly_wait(5)

        # 페이지 소스를 가져와 BeautifulSoup으로 파싱합니다.
        soup = BeautifulSoup(driver.page_source, 'lxml')

        # 카테고리 정보를 찾습니다.
        category_element = soup.select('span.VfPpkd-vQzf8d')
        category = category_element[3].text if category_element else 'Not Found'

        print(f"어플 카테고리: {category}")

        if category not in categories:
            categories[category] = i['totalUsageTime']
        else:
            categories[category] += i['totalUsageTime']

    # 브라우저를 닫습니다.
    driver.quit()

    output['characteristic']['interests'].append({'interestName': "Interest Name", 'interestPercent': 5000})
    # 통화, 카톡, 인스타 사용량으로 사교성
    # 카테고리 분류해서 관심사