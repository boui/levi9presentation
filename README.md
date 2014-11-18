Demo for a talk.

Akka-based crawler for wikipedia pages with Websocket to show results. 
Data is downloaded to hdfs, so you need it to run at hdfs://127.0.0.1:9000. 
All downloaded files are put in ~/files dir in hdfs. 
All links in files are rewritten at runtime to local file link, you can change it in CoordinatorActor, targetDir. 

After copying files from hdfs to targetDir you can surf them down to some level. 
This level is set in DownloadActor. 

Target wikipedia page is set in AppController.

To start it
>sbt
>container:start

open localhost:8081 and checkout javascript log
