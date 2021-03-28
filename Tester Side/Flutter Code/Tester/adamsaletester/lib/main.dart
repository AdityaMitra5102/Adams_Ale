import 'package:flutter/material.dart';
import 'package:geolocator/geolocator.dart';
import 'dart:io';
import 'dart:convert';
import 'button.dart';
import 'buttontapped.dart';
//import 'package:neumorphic/neumorphic.dart';

import 'package:flutter/services.dart';
//import 'screens/showcase.dart';
//import 'package:neumorphic/neumorphic.dart';
//import 'showcase/showcase.dart';
//import 'package:flutter_neumorphic/flutter_neumorphic.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "Adam's Ale Operator App",
      theme: ThemeData(
        primarySwatch: Colors.blue,
        scaffoldBackgroundColor: Colors.white,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: MyHomePage(title: "Adam's Ale Operator App"),
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
  var req = ' ';
  final Geolocator geolocator = Geolocator()..forceAndroidLocationManager;
  Position _currentPosition;
  void _myLocation() async {
    setState(() {
      location();
      req = 'appendDB?lat=' +
          '${_currentPosition.latitude}' +
          '&long=' +
          '${_currentPosition.longitude}' +
          '&EOF\r\n';
    });
    Socket.connect('52.172.129.174', 8080).then((socket) { //IP of cloud VM
      socket.add(utf8.encode(req));
      socket.flush();
    });
  }

  bool buttonPressed1 = false;

  void _letsPress1() {
    setState(() {
      buttonPressed1 = true;
    });
  }

  void location() {
    geolocator
        .getCurrentPosition(desiredAccuracy: LocationAccuracy.best)
        .then((Position position) {
      setState(() {
        _currentPosition = position;
        print(_currentPosition);
      });
    }).catchError((e) {
      print(e);
    });
  }

  @override
  Widget build(BuildContext context) {
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
                showDialog(
                  context: context,
                  builder: (BuildContext context) => _buildPopupDialog(context),
                );
                _letsPress1();
                _myLocation();
              },
              child: buttonPressed1
                  ? ButtonTapped(
                      text: "Submit Location",
                      icon: Icons.cloud_upload_outlined,
                    )
                  : MyButton(
                      text: "Submit Location",
                      icon: Icons.cloud_upload_outlined,
                    ),
            ),
          ],
        ),
      ),
    );
  }
}

Widget _buildPopupDialog(BuildContext context) {
  return new AlertDialog(
    content: Column(
      mainAxisAlignment: MainAxisAlignment.end,
      mainAxisSize: MainAxisSize.min,
      crossAxisAlignment: CrossAxisAlignment.center,
      children: <Widget>[
        Text("Location Submitted"),
      ],
    ),
  );
}
