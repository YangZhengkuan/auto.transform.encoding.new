# IDEA自动转码插件 - 解决文件乱码 (已适配新版本IDEA)

## 1. 背景

在项目开发的过程中，通常需要查看依赖Jar包中的源码，但是现在的项目的编码通常采用UTF-8编码，而在之前的开发中大部分采用的是GBK编码，这样我们在查看源码时就会出现打开文件乱码的问题，如下图所示：

![problem](./doc/problem.jpg)

这种问题的通常解决方案是：

1. 点击文件编码菜单
2. 选择正确的文件编码（如果选择的和 实际不一致，则需要再次尝试其他编码）
3. 点击Reload，重新载入文件，然后即可看到源码文件不再出现乱码

![before](./doc/before.gif)

文件乱码问题非常普遍，而上述解决方案又过于繁琐，还不一定能够一次性选择正确的文件编码，给开发人员带来极大的不便，因此如何自动识别文件编码并进行转码是目前亟待解决的重要问题，能够提高开发人员的开发效率，保证写代码过程的顺畅丝滑**[手动滑稽]**。


## 2. 解决方案

二话不说，先看最终效果图【打开源码瞬间即可自动转码，解放手指，摆脱疲劳 **^_^**】：

![before](./doc/after.gif)

具体解决方案如下：

开发IDEA插件，实现文件编码的正确识别以及转码动作的实时触发

1. 文件编码识别基于[cpdetector](http://cpdetector.sourceforge.net/)实现，可识别多种文件编码
2. 在IDEA中编辑器打开的所有文件均会对应一个VirtualFile【虚拟文件】，识别该对象的文件流的编码
3. 借鉴[IDEA Community](https://github.com/JetBrains/intellij-community)源码，自定义EncodingUtil，实现文件编码的设置
4. 实现监听器 FileChangedListener，监听IDEA编辑器中的文件切换事件【实现 FileEditorManagerListener】，在文件切换时，触发编码的识别及设置
5. 构建project的component，将上述监听器注册到项目的消息总线 project.messageBus 【实现 ProjectComponent】
6. 基于HashMap构建已识别的文件编码缓存，可有效提高插件的效率，不会重复执行文件流的编码识别过程

> 注：IDEA插件开发，有兴趣的同学可以参考[官网插件开发指导文档](https://link.jianshu.com/?t=http://www.jetbrains.org/intellij/sdk/docs/)

## 3. 项目地址

项目地址：https://github.com/YangZhengkuan/auto.transform.encoding

欢迎大家使用，感受顺畅丝滑般的体验！

如果有任何问题，请反馈给我 - **杨征宽(晚滨)** ，我会及时解答，并及时改进插件。


## 4. 安装方式

可以按照下图在IDEA中搜索本插件进行安装：
![install](./doc/install.gif)

您也可以在[JetBrains Plugins Repository](https://plugins.jetbrains.com/)中搜索本插件，进行本地安装。

## 5. 插件说明

**Auto Transform Encoding**

Identify the encoding of the current file in the editor, and transform the file encoding to the identified encoding automatically. Get rid of the the cumbersome steps you need to click the file encoding menu.
 
**文件编码自动转码插件**

自动识别文件的编码，并进行转码显示 摆脱需要在右下角编码菜单点击选择的繁琐步骤 

### Change Notes
- 1.6
    - New version adaptation
    
- 1.5
    - Ignore exception log
      
- 1.4
    - When user use the keyboard-shortcut(Alt + K) or menu to call this plugin, the action of this plugin is: If current encoding is not UTF-8, then transform to UTF-8; If current encoding is UTF-8, then transform to GBK. And the process of transform encoding automatically remain unchanged, it will also transform the file to the identified encoding automatically.
    - Change the usage of file encoding cache. About the identified file, if current encoding in the cache is different with right encoding, this plugin won't transform the file to the cached wrong encoding forcibly. Then, when the identified encoding is wrong, user can change encoding manually.
    - If the encoding identified by the jar of 'cpdetector' belongs to Big5、GB18030、GB2312, the file encoding will be transformed to GBK.

- 1.3

    - Upload the project to the github: https://github.com/YangZhengkuan/auto.transform.encoding
    - New version adaptation, Github: https://github.com/YangZhengkuan/auto.transform.encoding.new

- 1.2
    - Update plugin description.

- 1.1

    - Add the file encoding cache. Our plugin won't identify the the encoding of the file repeatedly.
    - Add the transform encoding judgment logic. When the existing encoding is correct, skip the encoding setting step.

- 1.0

    - Identify the encoding of the current file in the editor, and transform the file encoding to the identified encoding automatically.
    - Support the menu in the group of 'EditMenu', the anchor is the last. The menu name is 'AutoTransformEncoding'.
    - Support the keyboard-shortcut: Alt + K.
    
    
- 1.6
    - 新版本IDEA适配
    
- 1.5
    - 忽略异常日志打印   

- 1.4
    - 当用户通过快捷键（Alt + K）或菜单方式调用本插件时，本插件执行的动作为：若当前文件为非UTF-8编码，则切换为UTF-8编码；若当前文件编码为UTF-8，则切换为GBK编码。本插件的自动转码逻辑保持不变，会将文件自动转为所识别的编码。
    - 修正缓存使用方式，已经识别过编码的文件，在缓存中的编码和正确编码不一致时，不会强行将编码转为缓存中的错误编码。这样，当本插件识别的编码不正确时，用户可以手动更改文件的编码，不会被本插件强行转为错误的编码。
    - 将基于第三方包cpdetector识别的Big5、GB18030、GB2312编码均转为GBK编码。
      
- 1.3

    - 上传项目到Github: https://github.com/YangZhengkuan/auto.transform.encoding
    - 新版本IDEA适配：Github: https://github.com/YangZhengkuan/auto.transform.encoding.new
    
- 1.2

    - 更新插件文档说明
    
- 1.1

    - 添加文件编码缓存，不会重新识别已知编码的文件
    - 增加转码判断逻辑，现有编码即为正确编码时，跳过编码设置步骤
    
- 1.0

    - 自动识别并转换文件编码，丝滑般体验
    - 支持 菜单 Edit → AutoTransformEncoding(最后一项) 调用
    - 支持 Alt + K 快捷键调用

### Vendor

**YangZhengkuan 杨征宽（晚滨）**
 
**Github:** https://github.com/YangZhengkuan/auto.transform.encoding.new
