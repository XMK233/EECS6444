# -*- coding: utf-8 -*-
import os, re
import pandas as pd

# PATH_JDT = r"J:\EECS6444\Raw_Data\cvs_target\org.eclipse.jdt"
PATH_PLATFORM = r"J:\EECS6444\Raw_Data\cvs_target\org.eclipse.platform"
PATH_JDT_CORE = r"J:\EECS6444\Raw_Data\cvs\eclipse\org.eclipse.jdt.core"

def parse_file(PATH, root_PATH, path, file, encoding_method):
    if ".java,v" not in file:
        return
    ## get the lines from original files.
    lines = None
    with open(os.path.join(path, file), "r", encoding=encoding_method, errors="ignore") as f: # , encoding="utf-8"
        lines = f.readlines()
    ## skip the non-java file
    ## this is the latest version of this file.
    # print(re.split("\s", lines[0].replace(";", ""))[1])
    log_start = False
    full_code = []
    ##
    for l in lines:
        line = l.strip() #.lower()
        if log_start:
            full_code.append(line)
        ## log starts from here.
        if line == "text":
            log_start = True
        ## log ends here.
        if line == "@" and log_start == True:
            break
    code_content = "\n".join(full_code)[1:-1]
    ##
    file_name = root_PATH + os.path.join(path, file).replace(PATH, "").replace(",v", "").replace(os.sep, "-")
    with open(os.path.join(root_PATH, file_name), "w") as rf:
        rf.write(code_content)
    return

def read_through_all_the_files_in_a_path(PATH):
    root_PATH = PATH.split("\\")[-1]
    ## java files will be stored into this folder.
    if not os.path.exists(root_PATH):
        os.makedirs(root_PATH)
    group = os.walk(PATH)
    for path, directory, filelist in group:
        for file in filelist:
            try:
                parse_file(PATH, root_PATH, path, file, "utf-8")
            except:
                print("failed with utf-8: {}\n".format(os.path.join(path, file)))
#                 try:
#                     parse_file(PATH, root_PATH, path, file, "gbk")
#                 except:
#                     print("failed with gbk: {}\n".format(os.path.join(path, file)))
#                     try:
#                         parse_file(PATH, root_PATH, path, file, "gb18030")
#                     except:
#                         print("failed with gb18030: {}\n\n-----------------\n\n".format(os.path.join(path, file)))
    return

# read_through_all_the_files_in_a_path(PATH_PLATFORM)
read_through_all_the_files_in_a_path(PATH_JDT_CORE)