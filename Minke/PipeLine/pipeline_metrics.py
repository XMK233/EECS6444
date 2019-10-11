import os, re, shutil, javalang
import pandas as pd

def read_through_all_the_files_in_a_path(PATH):
    root_PATH = PATH.split("\\")[-1]
    ## java files will be stored into this folder.
    if not os.path.exists(root_PATH):
        os.makedirs(root_PATH)
    group = os.walk(PATH)
    for path, directory, filelist in group:
        for file in filelist:
            try:
                ## parse_file(PATH, root_PATH, path, file, "utf-8")
                if ".java" not in file:
                    continue
                new_file_name = root_PATH + os.path.join(path, file).replace(PATH, "").replace(",v", "").replace(os.sep, "#")
                shutil.copy(
                    os.path.join(path, file),
                    os.path.join(root_PATH, new_file_name)
                )
            except:
                print("failed with utf-8: {}\n".format(os.path.join(path, file)))
    return

def rename_java_files(root_PATH, new_root_PATH):
    """
    Rename the file as this format: package.name.java.
    Kim insist so. I don't know the meaning of naming like this.
    """
    if not os.path.exists(new_root_PATH):
        os.makedirs(new_root_PATH)
    # with open("{}_fileName_packageClassName.csv".format(root_PATH), "w") as fp:
    for code_file in os.listdir(root_PATH):
        try:
            with open(os.path.join(os.getcwd(), root_PATH, code_file), "r", encoding="utf-8") as cf:
                lines = cf.readlines()
            for line in lines:
                if "package " not in line:
                    continue
                match_rst = re.match("^package [\s\S]+;$", line)
                if match_rst == None:
                    continue
                ###
                package_name = match_rst.group().split()[1][0:-1]
                java_file_name = code_file.split("#")[-1]
                new_file_path = os.path.join(os.getcwd(), new_root_PATH, "{}.{}".format(package_name, java_file_name)  )
                # fp.write(
                #     "{},{}\n".format(
                #         code_file,
                #         os.path.join("{}.{}".format(package_name, java_file_name))
                #     )
                # )
                with open(new_file_path, "w") as ff:
                    ff.write("".join(lines))
                break
        except:
            print("failed: {}".format(code_file))

def parse_java_files_file_level_interface_class(PATH, root_PATH):
    with open("{}_count_interface_class.csv".format(root_PATH), "w") as ff:  ## file for field
        ff.write("{},{},{},{},{}\n".format("file", "num_of_anony", "num_of_interface", "num_of_class", "line_of_code"))
        ## start to iterate the files:
        for file in os.listdir(PATH):
            if ".java" not in file:
                continue
            #####
            ## within each code file:
            #####
            try:
                with open(os.path.join(PATH, file), "r") as pf:
                    content = pf.readlines()
                    code_string = "".join(content)
                    tree = javalang.parse.parse(code_string)
                    #####
                    counter_of_anony = "??"
                    #####
                    counter_interface = 0
                    ## https://github.com/c2nes/javalang/blob/master/javalang/tree.py
                    for path, node in tree.filter(javalang.tree.InterfaceDeclaration):
                        counter_interface += 1
                    #####
                    counter_class = 0
                    ## https://github.com/c2nes/javalang/blob/master/javalang/tree.py
                    for path, node in tree.filter(javalang.tree.ClassDeclaration):
                        counter_class += 1
                    ff.write("{},{},{},{},{}\n".format(file, counter_of_anony, counter_interface, counter_class,
                                                       len(content)))
            except:
                print("failed:", file)


def parse_java_files_class_level_method(PATH, root_PATH):
    with open("{}_method.csv".format(root_PATH), "w") as ff:  ## file for field
        ff.write(
            "{},{},{},{},{},{},{}\n".format(
                "file",
                "NOM_avg", "NOM_max", "NOM_total",
                "NSM_avg", "NSM_max", "NSM_total"
            )
        )
        ## start to iterate the files:
        for file in os.listdir(PATH):
            if ".java" not in file:
                continue
            #####
            ## within each code file:
            #####
            try:
                with open(os.path.join(PATH, file), "r") as pf:
                    content = pf.readlines()
                code_string = "".join(content)
                tree = javalang.parse.parse(code_string)
                normal = {
                    "max": 0,
                    "avg": 0,
                    "num_of_class": 0,
                    "total": 0
                }
                static = {
                    "max": 0,
                    "avg": 0,
                    "num_of_class": 0,
                    "total": 0
                }
                ## https://github.com/c2nes/javalang/blob/master/javalang/tree.py
                for path, node in tree.filter(javalang.tree.ClassDeclaration):
                    #####
                    ## within each class:
                    #####
                    ## methods:
                    normal["num_of_class"] += 1
                    static["num_of_class"] += 1
                    counter_method = 0
                    counter_method_static = 0
                    for path1, node1 in node.filter(
                            javalang.tree.MethodDeclaration):  ## node1 is each line of MethodDeclaration
                        if "static" in node1.modifiers:
                            counter_method_static += 1
                        else:
                            counter_method += 1
                    normal["max"] = normal["max"] if counter_method <= normal["max"] else counter_method
                    static["max"] = static["max"] if counter_method_static <= static["max"] else counter_method_static
                    normal["total"] += counter_method
                    static["total"] += counter_method_static
                normal["avg"] = normal["total"] / normal["num_of_class"] if normal[
                                                                                "num_of_class"] > 0 else 1  ### IF THE normal["num_of_class"] IS 0, THEN WE WILL MANUALLY CHANGE IT TO 1
                static["avg"] = static["total"] / static["num_of_class"] if normal[
                                                                                "num_of_class"] > 0 else 1  ### IF THE normal["num_of_class"] IS 0, THEN WE WILL MANUALLY CHANGE IT TO 1
                ff.write(
                    "{},{},{},{},{},{},{}\n".format(
                        file,
                        normal["avg"], normal["max"], normal["total"],
                        static["avg"], static["max"], static["total"],
                    )
                )
            except:
                print("failed:", file)
                continue


def parse_java_files_class_level_field(PATH, root_PATH):
    with open("{}_field.csv".format(root_PATH), "w") as ff:  ## file for field
        ff.write(
            "{},{},{},{},{},{},{}\n".format(
                "file",
                "NOF_avg", "NOF_max", "NOF_total",
                "NSF_avg", "NSF_max", "NSF_total"
            )
        )
        ## start to iterate the files:
        for file in os.listdir(PATH):
            if ".java" not in file:
                continue
            #####
            ## within each code file:
            #####
            try:
                with open(os.path.join(PATH, file), "r") as pf:
                    content = pf.readlines()
                code_string = "".join(content)
                tree = javalang.parse.parse(code_string)
                normal = {
                    "max": 0,
                    "avg": 0,
                    "num_of_class": 0,
                    "total": 0
                }
                static = {
                    "max": 0,
                    "avg": 0,
                    "num_of_class": 0,
                    "total": 0
                }
                ## https://github.com/c2nes/javalang/blob/master/javalang/tree.py
                for path, node in tree.filter(javalang.tree.ClassDeclaration):
                    #####
                    ## within each class:
                    #####
                    # print(node.name)
                    ## fields:
                    normal["num_of_class"] += 1
                    static["num_of_class"] += 1
                    counter_field = 0
                    counter_field_static = 0
                    for path1, node1 in node.filter(
                            javalang.tree.FieldDeclaration):  ## node1 is each line of FieldDeclaration
                        if "static" in node1.modifiers:
                            for variable_declarator in node1.declarators:  ## basically one declarator is just one line. Within 1 line, there can be several varaibles.
                                # print("static: ", node1.modifiers, variable_declarator.name)
                                counter_field_static += 1
                        else:
                            for variable_declarator in node1.declarators:  ## basically one declarator is just one line. Within 1 line, there can be several varaibles.
                                # print("not: ", node1.modifiers, variable_declarator.name)
                                counter_field += 1
                    normal["max"] = normal["max"] if counter_field <= normal["max"] else counter_field
                    static["max"] = static["max"] if counter_field_static <= static["max"] else counter_field_static
                    normal["total"] += counter_field
                    static["total"] += counter_field_static
                normal["avg"] = normal["total"] / normal["num_of_class"] if normal[
                                                                                "num_of_class"] > 0 else 1  ### IF THE normal["num_of_class"] IS 0, THEN WE WILL MANUALLY CHANGE IT TO 1
                static["avg"] = static["total"] / static["num_of_class"] if normal[
                                                                                "num_of_class"] > 0 else 1  ### IF THE normal["num_of_class"] IS 0, THEN WE WILL MANUALLY CHANGE IT TO 1
                ff.write(
                    "{},{},{},{},{},{},{}\n".format(
                        file,
                        normal["avg"], normal["max"], normal["total"],
                        static["avg"], static["max"], static["total"],
                    )
                )
            except:
                print("failed:", file)

# read_through_all_the_files_in_a_path(r"J:\EECS6444\Raw_Data\eclipse-sourceBuild-srcIncluded-3.0.1")
rename_java_files("eclipse-sourceBuild-srcIncluded-3.0.1", "eclipse_3.0.1")
# parse_java_files_class_level_field("eclipse_3.0.1", "eclipse")
# print("field finished")
# parse_java_files_class_level_method("eclipse_3.0.1", "eclipse")
# print("method finished")
# parse_java_files_file_level_interface_class("eclipse_3.0.1", "eclipse")
# print("file level finished")
