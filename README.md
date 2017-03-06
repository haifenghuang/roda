# Röda

## English

Röda is a homemade scripting language inspired by Bourne shell, Ruby and others. While not being a real shell language, Röda
still makes an extensive use of concurrency and streams (pipes). For more documentation, see GUIDE.md.
The standard library reference and other information is also found at the authors [web page](http://kaivos.org/~iikka/röda/doc). 

### Building

Using Gradle:

```sh
$ git clone --recursive https://github.com/fergusq/roda.git
$ cd roda
roda $ gradle fatJar
```

### Example

Prime generator:

```sh
#!/usr/bin/röda

main {
	primes := [2]
	seq 3, 10000 | { primes += i if [ i % p != 0 ] for p in primes } for i
	print p for p in primes
}
```

HTTP server:

```sh
#!/usr/bin/röda

{
	http := require("http_server")
}

main {
	server := new http.HttpServer(8080)
	server.controllers["/"] = http.controller({ |request|
		request.send "200 OK", "<html><head><title>Hello world!</title></head><body>Hello world!</body></html>"
	})
	while true; do
		server.update
	done
}
```

## Suomeksi

Röda on uusi ohjelmointikieleni, joka on saanut vaikutteensa lähinnä Bourne shellistä.
Dokumentaatio on tällä hetkellä saatavilla suomeksi tiedostossa OHJEET.md.
