def getFileContent(){
    def mf ="ls /Users/hongqizhang/workspace/ansibletest/releases  ".execute().text
    def myls = mf.readLines().collect{ it.split()[0].minus('.xml')}
    def map=[:]
    myls.each { file->
            my_tag="curl -k https://raw.githubusercontent.com/hqzhang/ansibletest/main/releases/${file}.xml".execute().text 
            map[ file]= my_tag
    }
    my_tag=map[SolutionDetail]
    return """
        <!--table><tr>
        <tr>
        <td class="setting-main"-->
            <textarea name=\"value\"  value  class=\"setting-input  \" type=\"text\">${my_tag}</textarea>
            <!--/td>
        </tr>
    </tr></table-->
    """
}

def getFileList(){
    def test=''
    def mf ="ls /Users/hongqizhang/workspace/ansibletest/releases  ".execute().text
    return mf.readLines().collect{ it.split()[0].minus('.xml')}
}