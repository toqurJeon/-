FROM python:3.8.10
COPY . ./
RUN pip install -U pip==20.3
RUN pip install --use-deprecated=legacy-resolver -r requirements.txt

RUN apt-get -y update
RUN apt -y install wget
RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
RUN apt -y install ./google-chrome-stable_current_amd64.deb

ENV PYTHONUNBUFFERED=0
ENTRYPOINT ["python3", "-u", "main.py"]