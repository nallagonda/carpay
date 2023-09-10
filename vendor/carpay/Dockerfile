#FROM python:3-slim-buster
# Use the official lightweight Python image.
FROM python:3.9-slim
# Allow statements and log 
#ENV PYTHONUNBUFFERED True

RUN mkdir /code

WORKDIR /code

COPY requirements.txt .

RUN pip install -r requirements.txt

COPY . .

CMD ["uvicorn", "main:app", "--host=0.0.0.0", "--port=80"]
