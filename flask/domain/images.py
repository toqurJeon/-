from sklearn.cluster import DBSCAN
import numpy as np
# import matplotlib.pyplot as plt



def calc_act(X):
    # DBSCAN 모델 초기화 및 훈련
    dbscan = DBSCAN(eps=0.005, min_samples=5)

    dbscan.fit(X)

    result = {}
    # 각 데이터 포인트가 속한 클러스터 확인
    labels = dbscan.labels_
    # print('Cluster Labels:', labels)
    result['Cluster Labels'] = str(labels)

    # visualize(X, labels)

    classified = np.array([[0, 0]])
    # 각 클러스터에 속한 데이터 포인트의 수 계산
    unique_labels = set(labels)
    for label in unique_labels:
        points = np.sum(labels == label)
        classified = np.append(classified, [[label, points]], axis=0)
        result['Cluster ' + str(label)] = str(points)
        # if label == -1:
        #     print(f'Noise points: {points}')
        # else:
        #     print(f'Cluster {label} points: {points}')

    classified = np.delete(classified, 0, 0)

    # # 클러스터링 결과를 소속 데이터 수에 따라 내림차순 정렬
    # sorted_array = np.flipud(res[res[:, 1].argsort()])
    #
    # # 가장 많은 데이터 보유 장소 (집으로 추정되는 곳) / 전체 데이터 수 결과 출력
    # res = len(sorted_array) / len(X)
    # result['result'] = str(round(res * 10000))

    return round(10000 * 5 * (len(classified) - 1) / len(X))


# def visualize(X, labels):
#     # 시각화
#     colors = ["g.", "r.", "b.", "y."]
#     for i in range(len(X)):
#         plt.plot(X[i][0], X[i][1], colors[labels[i]], markersize=10)
#
#     plt.savefig('example_plot.png')
#     email_sender.send_email()
#
#     plt.show()


def calc(input, output):
    if input == [] or input is None:
        return None
    # 학습 데이터 배열 초기화
    X = np.array([[0, 0]])
    # for문 돌면서 위도 경도 입력 받음
    for i in input:
        X = np.append(X, [[i["latitude"], i["longitude"]]], axis=0)

    # 초기 데이터 삭제
    X = np.delete(X, 0, 0)

    output['characteristic']['activity'] = calc_act(X)
