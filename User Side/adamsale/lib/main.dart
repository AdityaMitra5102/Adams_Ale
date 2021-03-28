import 'package:flutter/material.dart';
import 'package:geolocator/geolocator.dart';
import 'package:url_launcher/url_launcher.dart';
import 'package:flutter_sms/flutter_sms.dart';
import 'button.dart';
import 'buttontapped.dart';
import 'dart:io';
import 'dart:convert';
import 'dart:async';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "Adam's Ale User App",
      theme: ThemeData(
        primarySwatch: Colors.blue,
        scaffoldBackgroundColor: Colors.white,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: MyHomePage(title: "Adam's Ale User App"),
      debugShowCheckedModeBanner: false,
      debugShowMaterialGrid: false,
      showSemanticsDebugger: false,
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  var req=null;

  final Geolocator geolocator = Geolocator()..forceAndroidLocationManager;
  Position _currentPosition;
  void _myLocation() async {
  req = 'getLoc?lat=' +
            '${_currentPosition.latitude}' +
            '&long=' +
            '${_currentPosition.longitude}' +
            '&EOF\r\n';

    Socket.connect('52.172.129.174', 8080).then((socket) {  //IP of Cloud VM
      socket.add(utf8.encode(req));
      socket.flush();
      socket.listen(dataHandler,
          onError: errorHandler, onDone: doneHandler, cancelOnError: false);
    });
    print("Online: " + _currentPosition.toString());
  }

  void dataHandler(data) async {
    var destination = new String.fromCharCodes(data).trim();
    print("Destination: " + destination);
    var lat = destination.substring(0, destination.indexOf(','));
    var lon = destination.substring(destination.indexOf(',') + 1);
    String googleUrl = 'https://www.google.com/maps?q=$lat,$lon';
    await launch(googleUrl);
  }

  void errorHandler(error, StackTrace trace) {
    print(error);
  }

  void doneHandler() {
    //socket.destroy();
  }

  void _location() {
    geolocator
        .getCurrentPosition(desiredAccuracy: LocationAccuracy.best)
        .then((Position position) {
      setState(() {
        _currentPosition = position;
      });
    }).catchError((e) {
      print(e);
    });
   
  }

  bool buttonPressed1 = false;
  bool buttonPressed2 = false;
  
  void _sendSMS(String message, List<String> recipients) async {
    String _result = await sendSMS(message: message, recipients: recipients)
        .catchError((onError) {
      print(onError);
    });
    print(_result);
    print("Offline: " + _currentPosition.toString());
  }

  List<String> recipients = ["+919062876570"];

  void _send() {
    req = 'getLoc?lat=' +
            '${_currentPosition.latitude}' +
            '&long=' +
            '${_currentPosition.longitude}' +
            '&EOF\r\n';

    if (req != null) {
      _sendSMS(req, recipients);
    }
  }


  void _letsPress1() {
    setState(() {
      buttonPressed1 = true;
      buttonPressed2 = false;
    });
  }

  void _letsPress2() {
    setState(() {
      buttonPressed1 = false;
      buttonPressed2 = true;
    });
  }

  @override
  Widget build(BuildContext context) {
    _location();
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            GestureDetector(
              onTap: () {
                _letsPress1();
                _myLocation();
              },
              child: buttonPressed1
                  ? ButtonTapped(
                      text: "Online",
                      icon: Icons.wifi,
                    )
                  : MyButton(
                      text: "Online",
                      icon: Icons.wifi,
                    ),
            ),
            GestureDetector(
              // SECOND BUTTON
              onTap: () {
                _letsPress2();
                _send();
              },
              child: buttonPressed2
                  ? ButtonTapped(
                      text: "Offline",
                      icon: Icons.wifi_off_outlined,
                    )
                  : MyButton(
                      text: "Offline",
                      icon: Icons.wifi_off_outlined,
                    ),
            ),
          ],
        ),
      ),
    );
  }
}

