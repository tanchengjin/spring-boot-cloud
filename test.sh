#!/bin/bash

# Install ocserv
apt-get update
apt-get install ocserv

# Create configuration file
cat > /etc/ocserv/ocserv.conf << EOF
auth = "plain[/etc/ocserv/ocpasswd]"
tcp-port = 8443
udp-port = 8443
run-as-user = nobody
run-as-group = daemon
socket-file = /var/run/ocserv-socket
server-cert = /etc/ocserv/server-cert.pem
server-key = /etc/ocserv/server-key.pem
ca-cert = /etc/ocserv/ca-cert.pem
cert-user-oid = 0.9.2342.19200300.100.1.1
tls-priorities = "NORMAL:%SERVER_PRECEDENCE:%COMPAT:-VERS-SSL3.0"
max-clients = 16
max-same-clients = 2
keepalive = 32400
dpd = 90
mobile-dpd = 1800
try-mtu-discovery = true
cert-fail-reauth = true
predictable-ips = true
default-domain = example.com
rekey-time = 172800
rekey-method = ssl
use-occtl = true
pid-file = /var/run/ocserv.pid
isolate-workers = true
device = vpns
policies = /etc/ocserv/policies.conf
EOF

# Create certificate files
cd /etc/ocserv
certtool --generate-privkey --outfile ca-key.pem
cat > ca.tmpl << EOF
cn = "VPN CA"
organization = "Example Inc"
serial = 1
expiration_days = 3650
ca
signing_key
cert_signing_key
crl_signing_key
EOF
certtool --generate-self-signed --load-privkey ca-key.pem --template ca.tmpl --outfile ca-cert.pem
certtool --generate-privkey --outfile server-key.pem
cat > server.tmpl << EOF
cn = "vpn.example.com"
organization = "Example Inc"
expiration_days = 3650
signing_key
encryption_key
tls_www_server
EOF
certtool --generate-certificate --load-privkey server-key.pem --load-ca-certificate ca-cert.pem --load-ca-privkey ca-key.pem --template server.tmpl --outfile server-cert.pem

# Create user password file
echo "username:$(openssl passwd -crypt password)" > /etc/ocserv/ocpasswd