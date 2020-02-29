var app = require('express')();
var http = require('http').createServer(app);
var fs = require('fs');
var io = require('socket.io')(http);
var KisiSayisi=0;

app.get('/',function(req,res){

    res.sendFile(__dirname+'/bluechannel.html');

});


var den = ['DiziAd 1','DiziAd 2','DiziAd 3'];

io.on('connection',function(socket){

    socket.emit('KanalListesi',den);
    console.log(socket.id+' (Baglanti Kuruldu.)Bagli Kisi Sayisi = '+(++KisiSayisi));
    socket.emit('androidSohbet','Karsilama','HosGeldiniz');

socket.on('disconnect',function(){

    console.log('(Baglanti Bitti.)Bagli Kisi Sayisi = '+(--KisiSayisi));

});

    socket.on(den[0],function(mesaj){
        console.log('Sohbetten Gelen : '+mesaj);
//          client.set('db',mesaj,redis.print);
        io.emit('androidSohbet',den[0]+" | "+mesaj);
    });
    socket.on(den[1],function(mesaj){
        io.emit('androidSohbet',den[1],mesaj);
    });
    socket.on(den[2],function(mesaj){
        io.emit('androidSohbet',"KullaniciAdi\nMesaj\n",den[2],mesaj);
    });
    socket.on('Veri',function(data){
        io.emit('androidSohbet',data.kulAd,data.mesaj);
    });
});
io.on('disconnection',function(socket){

    console.log('Kullanici Cikti');

});



http.listen(8000,function(){

    console.log('8000 Portunda Calisiyoruz');
});