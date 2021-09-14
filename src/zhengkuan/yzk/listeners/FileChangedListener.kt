package zhengkuan.yzk.listeners

import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.vfs.VirtualFile
import zhengkuan.yzk.TransformEncodingAction

/**
 * 编辑器文件切换事件
 *
 * @author zhengkuan.yzk@alibaba-inc.com
 * @date 2018/6/20
 */
class FileChangedListener : FileEditorManagerListener {

    override fun fileOpened(source: FileEditorManager, file: VirtualFile) {
        TransformEncodingAction.transformFileEncoding(file)
    }

}