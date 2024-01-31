from sklearn.cluster import DBSCAN
import numpy as np


def count_result(sample_json):

    # 학습 데이터 배열 초기화
    X = np.array([[0, 0]])
    # 전체 데이터 개수 카운팅
    count = 0
    # for문 돌면서 위도 경도 입력 받음
    for i in sample_json:
        X = np.append(X, [[sample_json[i]["longitude"], sample_json[i]["latitude"]]], axis=0)
        count += 1

    # 초기 데이터 삭제
    X = np.delete(X, 0, 0)

    # DBSCAN 모델 초기화 및 훈련
    dbscan = DBSCAN(eps=50, min_samples=2)

    dbscan.fit(X)

    # 각 데이터 포인트가 속한 클러스터 확인
    labels = dbscan.labels_
    print('Cluster Labels:', labels)

    res = np.array([[0, 0]])
    # 각 클러스터에 속한 데이터 포인트의 수 계산
    unique_labels = set(labels)
    for label in unique_labels:
        if label != -1:  # 노이즈가 아니라면
            res = np.append(res, [[label, np.sum(labels == label)]], axis=0)

    res = np.delete(res, 0, 0)

    # 클러스터링 결과를 소속 데이터 수에 따라 내림차순 정렬
    sorted_array = np.flipud(res[res[:, 1].argsort()])
    print(sorted_array)

    # 가장 많은 데이터 보유 장소 (집으로 추정되는 곳) / 전체 데이터 수 결과 출력
    result = sorted_array[0][1] / count
    print(result)
    return result
