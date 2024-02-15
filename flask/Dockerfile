FROM python:3.8.10
COPY . ./
RUN pip install -U pip==20.3
RUN pip install --use-deprecated=legacy-resolver -r requirements.txt
RUN apt-get update
RUN apt-get -y install google-chrome-stable
ENTRYPOINT ["python3", "main.py"]