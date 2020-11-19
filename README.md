# nextgen
<h2>NextGen Payments System</h2>


<h3>Implementation Details:</h3>

A SpringBoot based standalone application which processes batch file(s). The current version supports `Add`, `Charge`, `Credit` as the batch file operations on Accounts. 


<h3>Request flow:</h3>

```
NextgenApplication -->  BatchFileProcessor -->  AccountService -->  AccountDao  -->  AccountCache (acts like DB)
```


<h3>Application Running Instructions:</h3>

Pass the batch file name as VM variable
```
-Dfile.name=txns
```
