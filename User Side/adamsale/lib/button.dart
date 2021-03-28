import 'package:flutter/material.dart';

class MyButton extends StatelessWidget {
  var icon;
  var text;

  MyButton({
    Key key,
    this.icon,
    this.text,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: EdgeInsets.all(15),
      child: Container(
        padding:
            EdgeInsets.only(left: 100.0, right: 100.0, top: 10.0, bottom: 10.0),
        child: Column(
          children: <Widget>[
            Icon(
              icon,
              size: 37,
              color: Colors.black54,
            ),
            Text(
              text,
            ),
          ],
        ),
        decoration: BoxDecoration(
          shape: BoxShape.rectangle,
          color: Colors.blue[300],
          boxShadow: [
            BoxShadow(
                color: Colors.blue[600],
                offset: Offset(4.0, 4.0),
                blurRadius: 15.0,
                spreadRadius: 1.0),
            BoxShadow(
                color: Colors.white,
                offset: Offset(-4.0, -4.0),
                blurRadius: 15.0,
                spreadRadius: 1.0),
          ],
          gradient: LinearGradient(
            begin: Alignment.topLeft,
            end: Alignment.bottomRight,
            colors: [
              Colors.blue[200],
              Colors.blue[300],
              Colors.blue[400],
              Colors.blue[500],
            ],
            stops: [0.1, 0.3, 0.8, 1],
          ),
        ),
      ),
    );
  }
}
