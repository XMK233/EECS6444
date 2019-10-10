## 2019-10-02 Minke
* I recommend that all the members will have a separate folder to store their own stuff. And then we keep modifying this file to say `I have done xxx part.`
* Plan: (if finished, use ~~delete line~~ to mark them) 
    * **Minke**: ~~extract java from cvs files; consider more conditions for regular expression~~; The results is in [Minke/Extract_Java_Code/Extract_Java_Code.zip](https://github.com/XMK233/EECS6444/blob/master/Minke/Extract_Java_Code/Extract_Java_Code.zip) 
    * **Kim**: test the metrics calculation tools; 
    * **Garg**: use bugID and bugzilla file to get the post- and pre-release bug list; 

## 2019-10-08 Minke
* I find that if we remove pattern, we actually can **find more bugIDs**. But the rationale may be questioned. Here I provide one version of the rationale:
	* The log is human written and no one can guarantee that he or she can capture all of the pattern. 
	* Even they managed to make it, such kind of methods may not be scalalbe or used under different production scenario. 
	* Comparing with leaving out some should-be-considered bugID, the inconsistency caused by our way (not considering pattern but extract numbers blindly) should be smaller. **This predication is not verified**. 
* Under dir: Minke\Extract_File_Commit_Info, you can find 2 new files: `org.eclipse.jdt.core_no_pattern.csv` and `org.eclipse.platform_no_pattern`. **Those files contains no patterns. It contains the number and dates of the  commits**. 

## 2019-10-10 Minke
* I have generated the post and pre release bug. In [https://github.com/XMK233/EECS6444/tree/master/Minke/Merge_Tables](https://github.com/XMK233/EECS6444/tree/master/Minke/Merge_Tables). 