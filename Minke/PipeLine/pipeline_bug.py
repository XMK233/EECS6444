import os, re
import pandas as pd

def read_through_all_the_files_in_a_path_choose_pattern(PATH, pattern_used = False):
    """
    You can choose whether you want to use regular expression to extract the bugID pattern.
    """
    root_PATH = PATH.split("\\")[-1]
    with open("{}_no_pattern.csv".format(root_PATH), "w") as rp:
        rp.write("{},{},{},{}\n".format("file", "fileVersion", "date","bugID"))
        group = os.walk(PATH)
        for path, directory, filelist in group:
            for file in filelist:
                try:
                    ## skip the non-java code:
                    if ".java,v" not in file:
                            continue
                    ## get the lines from original files.
                    lines = None
                    with open(os.path.join(path, file), "r", encoding="utf-8") as f:
                        lines = f.readlines()
                    ## skip the non-java file
                    ## this is the latest version of this file.
                    # print(re.split("\s", lines[0].replace(";", ""))[1])
                    log_start = False
                    version_extraction = True
                    version_start = False
                    ## the dict: versions is used to store the version info:
                    ## versions[a_certain_version] = {
                    ##     "date": None,
                    ##     "bugs": []
                    ## }
                    versions = {}
                    current_version = None
                    version_date = False
                    ##
                    for l in lines:
                        line = l.strip().lower()
                        ## starts from version_extraction, we start to extract version meta info
                        if version_extraction:
                            ## get the version line.
                            if line == "":
                                version_start = True
                            if line != "" and version_start == True:
                                version_start = False
                                current_version = line
                                versions[current_version] = {
                                    "date": None,
                                    "bugs": []
                                }
                                version_date = True
                            ## deal with date line. Some other meta info contained in this line as well. Parse them if needed.
                            if "date" in line and version_date == True:
                                version_date = False
                                date = re.split(r"\s*", line)[1].replace(";", "")
                                versions[current_version]["date"] = date
                            ## end of version meta info zone
                            if line == "desc":
                                version_start = False
                                version_extraction = False
                                current_version = None
                        ## lets' see if there is any version number line in recorded in versions.
                        if line in versions:
                            current_version = line
                        ## add the bugIDs, the bugID will be stored according to version.
                        if log_start:
                            ## no pattern is used
                            if not pattern_used:
                                rst_stage_1 = re.findall("(\d{4,})", line)
                                rst_stage_1 = [int(o) for o in rst_stage_1]
                                versions[current_version]["bugs"].extend(rst_stage_1)
                            ## some pattern is used
                            else:
                                if "fix" in line or "bug" in line: ## the condition here can be added.
                                    ## stage 1: the bug number will be like this: xxxx(space)(a series of numbers)(perhaps here are some other spaces)
                                    rst_stage_1 = re.findall(" (\d{4,})[\s]*", line)
                                    rst_stage_1 = [int(o) for o in rst_stage_1]
                                    versions[current_version]["bugs"].extend(rst_stage_1)
                                    ## stage 2: we want to see if there is any bug id in url.
                                    urls = re.findall("https://bugs.eclipse.org/bugs[\s\S]*", line)
                                    for url in urls:
                                        rst_stage_2 = re.findall("(\d{4,})", url)
                                        rst_stage_2 = [int(o) for o in rst_stage_2]
                                        versions[current_version]["bugs"].extend(rst_stage_2)
                        ## log starts from here.
                        if line == "log":
                            log_start = True
                        ## log ends here.
                        elif line == "@":
                            log_start = False
                    ## get rid of some useless stuff and then print the info.
                    del versions["desc"]
                    for version in versions:
                        version_info = versions[version]
                        date = version_info["date"]
                        version_info["bugs"] = list(set(version_info["bugs"]))
                        for bug in version_info["bugs"]:
                            rp.write(
                                "{},{},{},{}\n".format(
                                    root_PATH + os.path.join(path, file).replace(PATH, "").replace(",v", ""),
                                    version,
                                    date,
                                    bug
                                )
                            )
                except:
                    print("failed: {}".format(os.path.join(path, file)))
    return

def read_through_all_the_files_to_get_release_and_commit_No(PATH, pattern_used = False):
    """
    You can choose whether you want to use regular expression to extract the bugID pattern.
    """
    root_PATH = PATH.split("\\")[-1]
    with open("{}_release_commit.csv".format(root_PATH), "w") as rp:
        rp.write("{},{},{}\n".format("file", "releaseNo", "commitNo"))
        group = os.walk(PATH)
        for path, directory, filelist in group:
            for file in filelist:
                try:
                    ## skip the non-java code:
                    if ".java,v" not in file:
                            continue
                    ## get the lines from original files.
                    lines = None
                    with open(os.path.join(path, file), "r", encoding="utf-8") as f:
                        lines = f.readlines()
                    ##
                    symbols_start = False
                    for l in lines:
                        line = l.strip().lower()
                        if line == "symbols":
                            symbols_start = True
                            continue
                        if not symbols_start:
                            continue
                        if line == "locks; strict;":
                            symbols_start = False
                            continue
                        ### dealing with symbols
                        releaseNo, commitNo = l.strip().split(":")
                        rp.write(
                            "{},{},{}\n".format(
                                root_PATH + os.path.join(path, file).replace(PATH, "").replace(",v", ""),
                                releaseNo,
                                commitNo
                            )
                        )
                except:
                    print("failed: {}".format(os.path.join(path, file)))
    return

def change_versions(x):
    if x[-2:] == ".0":
        return "R{}".format( "_".join(   x[0:-2].split(".")) )
    else:
        return "R{}".format( "_".join(   x.split(".")) )

def merge_tables(TAB_PATH_1, TAB_PATH_2, TAB_PATH_3, Bug_List, how = "outer"):
    df1 = pd.read_csv(TAB_PATH_1)
    df1["date"] = df1["date"].map(lambda x: x[0:10].replace(".", "-"))  #pd.to_datetime(, format="%Y.%m.%d")
    df2 = pd.read_csv(TAB_PATH_2)
    df3 = pd.read_csv(TAB_PATH_3)
    df4 = pd.read_csv(Bug_List)
    ####
    df1.rename(columns={"fileVersion": "commitNo"}, inplace= True)
    df3.rename(columns={"Version": "releaseNo"}, inplace=True)
    df3["releaseNo"] = df3["releaseNo"].map(lambda x: change_versions(x))
    df4 = df4[["Bug ID", "Changed"]]
    df4["Changed"] = df4["Changed"].map(lambda x: x[0:10])
    df4.rename(columns={"Bug ID": "bugID"}, inplace=True)
    ####
    _ = pd.merge(df1,df2,on=['file','commitNo'],how= how)
    _1 = pd.merge(_, df3, on = ["releaseNo"], how = how)
    df_merge = pd.merge(_1, df4, on = ["bugID"], how = how)
    df_merge["date"] = pd.to_datetime(df_merge["date"], format = '%Y-%m-%d')
    df_merge["Date"] = pd.to_datetime(df_merge["Date"], format = '%Y-%m-%d')
    df_merge["Changed"] = pd.to_datetime(df_merge["Changed"], format = '%Y-%m-%d')
    ##
    df_merge["Changed-Date"] = df_merge["Changed"] - df_merge["Date"]
    ##
    pre_bug = df_merge[(df_merge['Changed-Date'].dt.days > -180) & (df_merge['Changed-Date'].dt.days <= 0)]
    post_bug = df_merge[(df_merge['Changed-Date'].dt.days > 0) & (df_merge['Changed-Date'].dt.days <= 180)]
    return df_merge, pre_bug, post_bug

def parse_file_to_map_cvsFileName_packageName(PATH, root_PATH, path, file, encoding_method):
    if ".java,v" not in file:
        return
    ## get the lines from original files.
    with open(os.path.join(path, file), "r", encoding=encoding_method, errors="ignore") as f: # , encoding="utf-8"
        lines = f.readlines()
    ## skip the non-java file
    ## this is the latest version of this file.
    # print(re.split("\s", lines[0].replace(";", ""))[1])
    ##
    for l in lines:
        line = l.strip() #.lower()
        if "package " not in line:
            continue
        match_rst = re.match("^package [\s\S]+;$", line)
        if match_rst == None:
            continue
        ###
        package_name = match_rst.group().split()[1][0:-1]
        java_file_name = file.replace(",v", "") # code_file.split("-")[-1]
        print(package_name, java_file_name)
        break

    ##
    file_name = root_PATH + os.path.join(path, file).replace(PATH, "").replace(",v", "").replace(os.sep, "-")
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
                parse_file_to_map_cvsFileName_packageName(PATH, root_PATH, path, file, "utf-8")
            except:
                print("failed with utf-8: {}\n".format(os.path.join(path, file)))
    return

# read_through_all_the_files_in_a_path_choose_pattern(r"J:\EECS6444\Raw_Data\cvs\eclipse")
# read_through_all_the_files_to_get_release_and_commit_No(r"J:\EECS6444\Raw_Data\cvs\eclipse")
## download the bug files from: https://bugs.eclipse.org/bugs/query.cgi?format=advanced
# df, pre_bug, post_bug = merge_tables(
#     r"eclipse_no_pattern.csv",
#     r"eclipse_release_commit.csv",
#     r"release-info.csv",
#     r"Bugzilla/Whole.csv",
#     "inner"
# )
# pre_bug.to_csv("eclipse_pre_bug.csv", index=False)
# post_bug.to_csv("eclipse_post_bug.csv", index=False)
read_through_all_the_files_in_a_path(r"J:\EECS6444\Raw_Data\cvs\eclipse")