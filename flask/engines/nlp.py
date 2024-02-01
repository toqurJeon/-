from nltk.sentiment.vader import SentimentIntensityAnalyzer

if __name__ == '__main__':
    # VADER SentimentIntensityAnalyzer 객체 생성
    analyzer = SentimentIntensityAnalyzer()

    # 감정 분석할 텍스트
    text = "Pork belly😁"

    # 감정 점수 계산
    scores = analyzer.polarity_scores(text)

    # Compound score 출력
    compound_score = scores['compound']
    print(f'Compound Score: {compound_score}')

    # Compound score에 따른 감정 분류
    if compound_score >= 0.05:
        print('Positive')
    elif compound_score <= -0.05:
        print('Negative')
    else:
        print('Neutral')
