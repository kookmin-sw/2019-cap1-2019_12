from selenium import webdriver
from selenium.common.exceptions import NoSuchElementException

category = ["소통", "팀워크", "도전", "능동", "성실", "정직", "인내심", "창의", "글로벌역량", "주인의식"]#10개
keyword = ["소통", "대화", "교류",
           "팀워크", "협력", "협동",
           "도전", "모험", "용기",
           "능동", "열정", "적극",
           "성실", "근면", "충실",
           "정직", "진실", "솔직",
           "인내심", "끈기", "근성",
           "창의", "창조", "독창",
           "문화", "국제", "외국",
           "책임", "의무", "자기주도"]#30개

driver = webdriver.Chrome("C:/Users/abcd_/workspace/chromedriver.exe")
driver.get("https://search.naver.com/search.naver?where=news&sm=tab_jum")

# 검색옵션 열어놓기
elem = driver.find_element_by_id("_search_option_btn")
elem.click()

for i in range(len(keyword)):
    #검색창 clear
    driver.find_element_by_id("nx_query").clear()

    # 검색창 입력
    elem = driver.find_element_by_id("nx_query")
    elem.send_keys(keyword[i])

    # 검색클릭
    elem = driver.find_element_by_class_name("bt_search")
    elem.click()

    # 기간옵션클릭
    elem = driver.find_element_by_xpath('//*[@id="snb"]/div/ul/li[2]')
    elem.click()

    # 1년 클릭
    elem = driver.find_element_by_xpath('//*[@id="_nx_option_date"]/div[1]/ul[1]/li[6]')
    elem.click()

    filename = category[i // 3]
    savePath = "C:/news/" + filename + ".txt"
    saveFile = open(savePath, 'a', encoding='utf8')

    ###############300페이지 뉴스 끌어오기
    for page in range(300):
        news = driver.find_element_by_class_name("type01")
        news = news.find_elements_by_tag_name("li")

        for j in range(len(news)):
            article_id = news[j].get_attribute("id")
            if article_id == "":
                continue

            xpath = '//*[@id="'+article_id+'"]/dl'

            try:
                #기사제목
                title = news[j].find_element_by_xpath(xpath+'/dt/a').get_attribute("title")
                saveFile.write(title)
                print(title)

                #본문
                article = news[j].find_element_by_xpath(xpath+'/dd[2]').text
                saveFile.write(article)
                print(article)

            except NoSuchElementException:
                print("Error")
                continue

        driver.find_element_by_class_name("next").click()
    ##############

    saveFile.close()

