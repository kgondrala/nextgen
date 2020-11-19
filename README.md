# NextGen Payments System


<h3>Implementation Details:</h3>
A SpringBoot based commandline application which processes batch file(s). The current version supports <b>Add</b>, <b>Charge</b>, <b>Credit</b> as the batch file operations on Accounts. 


<h3>NextgenApplication:</h3>
A SpringBoot based commandline application which processes batch file(s). 


<h3>BatchFileProcessor:</h3>
Reads the batch file, and converts all the file records to AccountActions. All these AccountActions are processed and a Batch response is generated.


<h3>Request flow:</h3>

```
NextgenApplication -> BatchFileProcessor -> AccountService -> AccountDao -> AccountCache (acts like DB)
```


<h3>Application Running Instructions:</h3>

Pass the batch file name as VM variable
```
-Dfile.name=txns
```
