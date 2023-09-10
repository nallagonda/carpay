const http = require("http");
const dotenv = require("dotenv");
const cors = require("cors");

const querystring = require("querystring");

dotenv.config({
  path: "./config.env",
});

const express = require("express");
const app = express();
const socketUtils = require("./utils/socketUtils");

const server = http.createServer(app);
const io = socketUtils.sio(server);
socketUtils.connection(io);

const socketIOMiddleware = (req, res, next) => {
  req.io = io;

  next();
};

// CORS
app.use(cors());

// ROUTES
app.use("/api/v1/send_authorization_approval", socketIOMiddleware, (req, res) => {
  const params = querystring.parse(req.url);
  //req.io.emit("message", `POS, ${req.orginalUrl}`);
  //req.io.emit("message", `POS, ${params['owner_token']}`);
  req.io.emit("message", params['/owner_token']);

  console.log("Authorization Successfull !!!",params['/owner_token']);
  res.send("Authorization Successfully Sent!");

});

// LISTEN
const port = process.env.PORT || 8000;
server.listen(port, () => {
  console.log(`App running on port ${port}...`);
});
