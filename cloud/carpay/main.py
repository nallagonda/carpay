from pathlib import Path
from typing import Optional
from fastapi import FastAPI, File, UploadFile, Request, WebSocket, HTTPException
from starlette.staticfiles import StaticFiles
from service.sei import *
from service.fcm_python import *
from motor.motor_asyncio import AsyncIOMotorClient
from pydantic import BaseModel
import requests
import json
import datetime

#import asyncio
#import nest_asyncio

app = FastAPI()
UPLOAD_DIR = Path("uploads")

server_token = 'AAAA9d2mwCY:APA91bFTX8abdqB5jSn9gzGo11NI1PDw6yMikvuNBBu_QKvbW32XrXfZTWd0kngqnn2zWgJVFnpbQIgzrGzJYVdQ3_cU70s3lHELZq4tKAao1QOEAWD36hqiosBXgvvS5BysAsZQxhVu'
device_token = 'd1Xz3bSLTM2-VEKkMLhgU3:APA91bGlK9v-34W4AhBaH0kPOGqUoHEQpT4SukhyS7uM7D4HcJ0rB_RMRPQSCVyb5bQFBvesuruRAMC3rHyiGgE5FtidywDctQaonO0v61-NBBy7BFKlXzpcDMykN3N-eDL8xPcKox15'

# MongoDB connection URL
MONGO_URL = "mongodb://localhost:27017"
client = AsyncIOMotorClient(MONGO_URL)
database = client["carpay-db"]
collection = database["accounts"]

#nest_asyncio.apply()

class Account(BaseModel):
    name: str
    licnum: str
    phone: str
    device_id: str
    blockchain_token: str

@app.post("/signup_with_image")
async def signup_with_image(file: UploadFile, name: str, licnum: str, phone: str):
    """Upload an image file."""

    # Validate the file
    if not file.content_type.startswith("image/"):
        raise ValueError("File is not an image")

    # Save the file to the directory
    filename = file.filename
    file_path = UPLOAD_DIR / filename
    file.write(file_path) # use Block chain / SEI to store this image along with values.

    # Encode the file name to UTF-8
    filename = filename.encode("utf-8")

    bc_token =  sei_blockchain_create(file, name, licnum, phone)
    print(" bc_token  from sei blockchain                        :   " + bc_token)
    
    account_data = {
        "name": name,
        "licnum": licnum,
        "phone": phone,
        "device_id": "dd",
        "blockchain_token": "bt"
    }

    account = Account(**account_data)

    result = await collection.insert_one(account.dict())
    
    print("After Account inserted into MongoDB ", account)

    return {
        "filename": filename,
        "file_size": file.size,
        "name": name,
        "licnum": licnum,
        "phone": phone,
        "bc_token": bc_token
    }

@app.get("/verify_car_licence")
async def verify_car_licence(image_text: str):

    is_match =  verify_image_text_service(image_text)
    res = " image text : " + image_text + " matched in sei blockchain :   "+ str(is_match)
    
    print(res)

    #import pdb;
    #pdb.set_trace()

    # if is_match call firebase "show_authorize"
    today = datetime.datetime.now()

    title = 'Carpay - Gasbill for the date - ' + today.strftime("%Y-%m-%d %H:%M:%S")
    body = 'You entered Costco Santa Clara!!! Can you confirm your car license plate : ', image_text

    response = send_push_notification(server_token, device_token, title, body)

    print(title)
    print(response)

    return is_match

@app.post("/authorize")
async def authorize(owner_token: str, is_authorized: bool):
    #is_match =  verify_image_text_service(image_text)
    res = " owner_token : " + owner_token + " is_athorized :   "+ str(is_authorized)
    print(res)

    response = send_authorization_approval(owner_token, is_authorized)

    return response

@app.get("/receipt")
async def accept_receipt_json(info: Request):
    json_data = await info.json()
    print(json_data)
    return {"message": "JSON data received"}

@app.get("/start_sales")
async def start_sales(owner_token: str, name: str):
    #is_match =  verify_image_text_service(image_text)
    res = " owner_token : " + owner_token + " is_athorized :   "+ str(is_authorized)
    print(res)
    return is_authorized

@app.websocket("/ws")
async def websocket(websocket: WebSocket):
    # Accept the connection from the client.
    await websocket.accept()

    # Receive messages from the client.
    while True:
        message = await websocket.receive()
        print(message)

        # Send a message back to the client.
        await websocket.send("Hello from the server!")

@app.get("/firebase")
def firebase():

    title = 'Carpay Owner token'
    body = 'Testing firebase integration'

    response = send_push_notification(server_token, device_token, title, body)
    print(response)

@app.post("/signup_with_image_test")
async def signup_with_image_test(name: str, licnum: str, phone: str):
    """Upload an image file."""

    bc_token =  sei_blockchain_create_test(name, licnum, phone)
    print(" bc_token  from sei blockchain                        :   " + bc_token)
    
    account_data = {
        "name": name,
        "licnum": licnum,
        "phone": phone,
        "device_id": "dd",
        "blockchain_token": "bt"
    }

    account = Account(**account_data)

    result = await collection.insert_one(account.dict())
    #account.id = str(result.inserted_id)
    print("After Account inserted into MongoDB ", account)

    return {
        "name": name,
        "licnum": licnum,
        "phone": phone,
        "bc_token": bc_token
    }


#@app.get("/")
#def read_root():
#    return {"message": "Hello, world!"}

# Serve the static files from the /static directory
app.mount("/static", StaticFiles(directory="static"), name="static")





