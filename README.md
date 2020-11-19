# nextgen
<h2>NextGen Payments System</h2>


<h3>Implementation Details:</h3>

A SpringBoot based standalone application which processes batch file. The current implementation supports Add, Charge, Credit as the batch file operations. 


<h3>Request flow:</h3>

```
NextgenApplication   -->    BatchFileProcessor   -->    AccountService   -->    AccountDaoe   -->    AccountCache

```


<h3>Application Running Instructions:</h3>

Pass the batch file name as VM variable
```
-Dfile.name=txns
```
