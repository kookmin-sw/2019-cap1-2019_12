from selenium import webdriver
from selenium.common.exceptions import NoSuchElementException
from datetime import datetime

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

#오늘, 일년전 날짜 계산
today = datetime.today()
past = datetime(today.year-1, today.month, today.day)
today = today.strftime("%Y.%m.%d")
past = past.strftime("%Y.%m.%d")

baseUrl = "https://section.blog.naver.com/Search/Post.nhn"
baseUrl = baseUrl + "?startDate="+past+"&endDate="+today + "&rangeType=PERIOD"

driver = webdriver.Chrome("C:/Users/abcd_/workspace/chromedriver.exe")
driver.get(baseUrl)

for i in range(len(keyword)):
    #파일 경로지정&오픈
    filename = category[i//3]

    savePath = "C:/blog/" + filename + ".txt"
    saveFile = open(savePath, 'a', encoding='utf-8')

    # 검색창 clear
    driver.find_element_by_name("sectionBlogQuery").clear()

    # 검색창 입력
    elem = driver.find_element_by_name("sectionBlogQuery")
    elem.send_keys(keyword[i])

    # 검색클릭
    elem = driver.find_element_by_xpath('//*[@id="header"]/div[1]/div/div[2]/form/fieldset/a[1]')
    elem.click()

    ###############300페이지 블로그 끌어오기
    for page in range(1, 301):
        blogs = driver.find_elements_by_class_name("list_search_post")
        for blog in blogs:
            try:
                #제목
                title = blog.find_element_by_class_name("title").text
                saveFile.write(title)
                print(title)

                #본문
                body = blog.find_element_by_class_name("text").text
                saveFile.write(body)

            except NoSuchElementException:
                print("Error")
                continue

        driver.get(baseUrl + "&pageNo=" + str(page+1)+"&keyword="+keyword[i])
    ###############

    saveFile.close()


