from bs4 import BeautifulSoup
from selenium import webdriver


def calc(input, output):
    if input == [] or input is None:
        return None

    # ChromeDriver를 자동으로 설치합니다.
    # chromedriver_autoinstaller.install()

    options = webdriver.ChromeOptions()
    options.add_argument('--headless')
    options.add_argument('--no-sandbox')
    options.add_argument('--disable-dev-shm-usage')
    driver = webdriver.Chrome(options=options)
    #driver = webdriver.Chrome()

    categories = {}
    total_time = 0
    for i in input:
        if 'youtube' in i['packageName'] or i['totalUsageTime'] == 0:
            continue
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
        if '인기' in category:
            category = category_element[4].text

        # print(f"어플 카테고리: {category}")

        if category not in categories:
            categories[category] = i['totalUsageTime']
        else:
            categories[category] += i['totalUsageTime']

        total_time += i['totalUsageTime']

    # 브라우저를 닫습니다.
    driver.quit()

    del categories['Not Found']

    sorted_categories = dict(sorted(categories.items(), key=lambda item: item[1], reverse=True))
    print(sorted_categories)

    # for category_name, value in sorted_categories.items():
    #     output['characteristic']['interests'].append({'interestName': category_name,
    #                                                   'interestPercent': round(10000*value/total_time)})
    # 통화, 카톡, 인스타 사용량으로 사교성
    # 카테고리 분류해서 관심사
