
# -*- conding: utf-8 -*-

import sundule as sd

data = sd.read_data('dataset/input_resume.txt')
score = sd.key_score(data)
sim = sd.cos_sim(score)

print('사용자 자소서와의 유사도 값:', sim)

