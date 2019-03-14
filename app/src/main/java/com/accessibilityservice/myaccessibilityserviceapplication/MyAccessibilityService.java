package com.accessibilityservice.myaccessibilityserviceapplication;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

public class MyAccessibilityService extends AccessibilityService {

    AccessibilityServiceInfo info;
    private String TAG = "TagggMyAccessibilityService";

    @SuppressLint("RtlHardcoded")
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate()");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.e(TAG, "ACC::onAccessibilityEvent: " + event.getEventType());
        AccessibilityNodeInfo source = event.getSource();
        String viewIdName;
        if(source != null) {
            viewIdName = source.getViewIdResourceName();
        }else{
            viewIdName = "NO Source, NO VIEW ID";
        }
        Log.d("ViewID", (viewIdName != null) ? viewIdName : "NO VIEW ID");
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
            if (event.getPackageName().equals("com.whatsapp")) {
                AccessibilityNodeInfo currentNode = getRootInActiveWindow();
                getNodeInfo(currentNode, "currentNode");

                /*if (currentNode != null) {
                    if (currentNode.getParent() != null) {
                        Toast.makeText(getApplicationContext(), "In Parent", Toast.LENGTH_SHORT).show();
                        getNodeInfo(currentNode.getParent());
                        Log.e("Taggggg", "In Parent");

                    } else {
                        Toast.makeText(getApplicationContext(), "No Parent", Toast.LENGTH_SHORT).show();
                        Log.e("Taggggg", "No Parent");
                    }
                }*/

                AccessibilityNodeInfo nodeInfo = event.getSource();
                Log.e("ViewIDTaggg", "nodeInfo=" + nodeInfo);
                if (nodeInfo == null) {
                    return;
                }
                if(nodeInfo.isClickable()){
                    nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }

                List<AccessibilityNodeInfo> lists = nodeInfo
                        .findAccessibilityNodeInfosByViewId("android:id/buttons");
                for (AccessibilityNodeInfo node : lists) {
                    Log.e("ViewIDTaggg", "left_buttons " + node);
                    node.performAction(AccessibilityNodeInfo.ACTION_SET_SELECTION);
//                    getNodeInfo(node, "currentNode");

                }
                List<AccessibilityNodeInfo> list = nodeInfo
                        .findAccessibilityNodeInfosByViewId("android:id/buttons");
                for (AccessibilityNodeInfo node : list) {
                    Log.e("ViewIDTaggg", "left_button " + node);
                    node.performAction(AccessibilityNodeInfo.ACTION_SET_SELECTION);
//                    getNodeInfo(node, "currentNode");

                }
                if (currentNode!=null && currentNode.getClassName().equals("android.widget.FrameLayout")
                        && currentNode.getChildCount() > 2 &&currentNode.getChild(2)!=null
                        && currentNode.getChild(2).getClassName().equals("android.widget.TextView")
                        && currentNode.getChild(2).getContentDescription().equals("Search")) {
                    currentNode.getChild(2).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
                currentNode.getChild(0).getChild(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);

            }
        }
//        AccessibilityNodeInfo currentNode=getRootInActiveWindow();
        /*if (event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
            if (event.getPackageName().toString().equals("com.whatsapp")){
                StringBuilder message = new StringBuilder();
                if (!event.getText().isEmpty()) {
                    for (CharSequence subText : event.getText()) {
                        message.append(subText);
                    }
                    if (message.toString().contains("Message from")){
                        String name = message.toString().substring(13);
                    }
                }
            }
        }*/
    }

    private void getNodeInfo(AccessibilityNodeInfo currentNode, String node) {
        if (currentNode != null) {
//            Toast.makeText(getApplicationContext(), "Show Node Info", Toast.LENGTH_SHORT).show();
            Log.e("Taggggg", "Show Node Info");
            for (int i = 0; i < currentNode.getChildCount(); i++) {
                Log.e("ViewIDTagggg", node+" Node Child at "+ i + " : " + currentNode);
                for (int j = 0; j < currentNode.getChildCount(); j++) {
                    getNodeInfo(currentNode.getChild(j), i+" CHILD "+j+" : ");
                }
            }
        }else{
//            Toast.makeText(getApplicationContext(), "No Node Info", Toast.LENGTH_SHORT).show();
            Log.e("Taggggg", "No Node Info");
        }
    }

    @Override
    public void onInterrupt() {
        Log.e(TAG, "onInterrupt");
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.e(TAG, "onServiceConnected");
    }

    /*public static void logViewHierarchy(AccessibilityNodeInfo nodeInfo, final int depth) {

        if (nodeInfo == null) return;

        String spacerString = "";

        for (int i = 0; i < depth; ++i) {
            spacerString += '-';
        }
        //Log the info you care about here... I choce classname and view resource name, because they are simple, but interesting.
        Log.d("TAG", spacerString + nodeInfo.getClassName() + " " + nodeInfo.getViewIdResourceName());

        for (int i = 0; i < nodeInfo.getChildCount(); ++i) {
            logViewHierarchy(nodeInfo.getChild(i), depth+1);
        }
    }*/

}
