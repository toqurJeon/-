FROM python:3.8.10
COPY . ./
RUN pip install -U pip==20.3
RUN pip install --use-deprecated=legacy-resolver -r requirements.txt

RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add -
RUN sh -c 'echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list'
RUN apt-get update
RUN apt-get -y install google-chrome-stable
ENTRYPOINT ["python3", "main.py"]