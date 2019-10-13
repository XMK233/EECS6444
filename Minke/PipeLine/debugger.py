import os, re
import pandas as pd
## testing the read_through_....No_1() function.
def read_through_all_the_files_to_get_release_and_commit_No_1(PATH, pattern_used = False):
    """
    You can choose whether you want to use regular expression to extract the bugID pattern.
    """
    root_PATH = PATH.split("\\")[-1]
    with open("{}_mainRelease_fastRelease_commit_____.csv".format(root_PATH), "w") as rp:
        rp.write("{},{},{},{}\n".format("file", "releaseNo", "fastReleaseNo", "commitNo"))
        group = os.walk(PATH)
#         file_rel_commit = []
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
                    release_commit = []
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
                        release_commit.append(l.strip())

                    current_version = None
                    for vstr in reversed(release_commit):
                        if ":" not in vstr:
                            continue
                        vstr = vstr.replace(";", "")
                        releaseNo, commitNo = vstr.split(":")
                        if re.findall("^R[\d_]+$", releaseNo):
                            current_version = releaseNo
                        if current_version != None:
                            print(
                                "{},{},{},{}\n".format(
                                    root_PATH + os.path.join(path, file).replace(PATH, "").replace(",v", ""),
                                    current_version,
                                    releaseNo,
                                    commitNo
                                )
                            )
                except:
                    print("failed: {}".format(os.path.join(path, file)))
            # break
    return

read_through_all_the_files_to_get_release_and_commit_No_1(r"J:\EECS6444\Raw_Data\cvs\eclipse")