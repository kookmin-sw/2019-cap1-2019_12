from selenium import webdriver
from selenium.common.exceptions import NoSuchElementException
from datetime import datetime
import csv

category = ['글로벌역량', '능동', '도전', '성실', '소통', '인내심', '정직', '주인의식', '창의', '팀워크']#10개
keyword = ["문화", "국제", "외국",
           "능동", "열정", "적극",
           "도전", "모험", "용기",
           "성실", "근면", "충실",
           "소통", "대화", "교류",
           "인내심", "끈기", "근성",
           "정직", "진실", "솔직",
           "책임", "의무", "자기주도",
           "창의", "창조", "독창",
           "팀워크", "협력", "협동"]#30개

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
    label = (i//3)+1
    #파일 경로지정&오픈
    filename = category[label-1]

    savePath = "C:/blog/" + filename + ".csv"
    saveFile = open(savePath, 'w', encoding='utf-8', newline='')

    csv_writer = csv.writer(saveFile)
    csv_writer.writerow(["label", "sentence"])

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
                title = blog.find_element_by_class_name("title").text.replace(",", "").replace(";", "")
                print(title)
                csv_writer.writerow([label, title])

                #본문
                body = blog.find_element_by_class_name("text").text.replace("\n", " ").replace(",", "").replace(";", "")
                csv_writer.writerow([label, body])

            except NoSuchElementException:
                print("Error")
                continue

        driver.get(baseUrl + "&pageNo=" + str(page+1)+"&keyword="+keyword[i])
    ###############

    saveFile.close()
