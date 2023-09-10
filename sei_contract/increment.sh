if [ -z "${keyname}" ];
then keyname=CarPay_Wallet2
fi 
if [ -z "${password}" ];
then password="12345678\n"
fi 

seid=~/go/bin/seid
contract='sei18d22szw3n84cq7qy9v625e08j0eh2h4zfzqg0um54a2k3l06agzqws2m5v' 
# Fill in contract address from deployment step, ex. sei14hj2tavq8fpesdwxxcu44rty3hh90vhujrvcmstl4zr3txmfvw9sh9m79m

increment_resp=$(printf $password |$seid tx wasm execute $contract '{"increment":{}}' --from $keyname --broadcast-mode=block  --chain-id atlantic-2 --gas=30000000 --fees=300000usei -y --node=https://rpc.atlantic-2.seinetwork.io/)

new_count=$($seid q wasm contract-state smart $contract '{"get_count":{}}' | grep -A 1 "count:" | awk -F: '/count:/{getline; print $2}')
printf "New count: %s\n" "$new_count"
