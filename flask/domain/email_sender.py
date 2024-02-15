from selenium import webdriver
from selenium.webdriver import ActionChains
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as EC
import time
import pyperclip
from selenium.webdriver.support.wait import WebDriverWait


def send_email():
    driver = webdriver.Chrome()

    # 네이버 로그인 페이지 열기
    driver.get('https://nid.naver.com/nidlogin.login')
    time.sleep(2)

    # 네이버 로그인
    naver_id = 'wjswwkd'
    naver_pw = 'Lvp2016!'

    # id
    id_input = driver.find_element(By.ID, 'id')
    id_input.click()
    pyperclip.copy(naver_id)
    actions = ActionChains(driver)
    actions.key_down(Keys.CONTROL).send_keys('v').key_up(Keys.CONTROL).perform()
    time.sleep(2)

    # pw
    pw_input = driver.find_element(By.ID, 'pw')
    pw_input.click()
    pyperclip.copy(naver_pw)
    actions = ActionChains(driver)
    actions.key_down(Keys.CONTROL).send_keys('v').key_up(Keys.CONTROL).perform()
    time.sleep(2)

    driver.find_element(By.ID, 'log.login').click()
    time.sleep(2)

    # try:
    #     element = WebDriverWait(driver, 10).until(
    #         EC.presence_of_element_located((By.CSS_SELECTOR, "span.btn_cancle"))
    #     )
    #     element.click()
    # except:
    #     print("cannot find")

    # 메일 쓰기 페이지로 이동
    # driver.get('https://mail.naver.com/v2/new')
    # time.sleep(2)
    #
    # # 받는 사람 입력
    # receiver_email = 'wjswwkd@naver.com'
    # email_input = driver.find_element(By.CSS_SELECTOR, '#recipient_input_element')
    # email_input.click()
    # pyperclip.copy(receiver_email)
    # actions = ActionChains(driver)
    # actions.key_down(Keys.CONTROL).send_keys('v').key_up(Keys.CONTROL).perform()
    # time.sleep(2)


    # 제목 입력
    subject = '이미지 파일 첨부 테스트'
    driver.find_element(By.CSS_SELECTOR, '.mWrite_subject .subject').send_keys(subject)

    # 내용 입력
    message = '이미지 파일을 첨부합니다.'
    driver.switch_to.frame(driver.find_element(By.CSS_SELECTOR, '.se2_input_wysiwyg'))
    driver.find_element(By.CSS_SELECTOR, 'body').send_keys(message)
    driver.switch_to.default_content()

    # 이미지 파일 첨부
    attachment_path = 'example_plot.png'
    driver.find_element(By.CSS_SELECTOR, '.attach .btn_file').send_keys(attachment_path)

    # 전송 버튼 클릭
    driver.find_element(By.CSS_SELECTOR, '.btn-toolbar .se2_send').click()

    # 잠시 대기 후 브라우저 종료
    time.sleep(3)
    driver.quit()
