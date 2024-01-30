from sklearn.cluster import DBSCAN
import json
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt


def count_result(X):
    df = pd.read_json('test.json')

    # DBSCAN 모델 초기화 및 훈련
    dbscan = DBSCAN(eps=2, min_samples=2)
    dbscan.fit(X)

    # 각 데이터 포인트가 속한 클러스터 확인
    labels = dbscan.labels_

    return dbscan
