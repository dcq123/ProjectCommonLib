package cn.qing.soft.appcommonlib.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rey.material.widget.Button;

import cn.qing.soft.appcommonlib.R;
import cn.qing.soft.appcommonlib.views.DilatingDotsProgressBar;


/**
 * 显示加载中、无数据、无网络时的公用界面
 */
public class ProgressLayout extends RelativeLayout {

    LayoutInflater inflater;
    View view;
    LayoutParams layoutParams;

    RelativeLayout loadingStateRelativeLayout;
    DilatingDotsProgressBar loadingStateProgressBar;

    RelativeLayout emptyStateRelativeLayout;
    RelativeLayout errorStateRelativeLayout;
    ImageView emptyIcon;
    Button errorStateButton;

    int loadingStateBackgroundColor;

    int emptyStateBackgroundColor;

    int errorStateBackgroundColor;

    private State state = State.CONTENT;
    /**
     * 各种状态下的布局使用圆角的资源ID
     */
    private int cornerResId = -1;

    public ProgressLayout(Context context) {
        super(context);
    }

    public ProgressLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ProgressLayout);

        loadingStateBackgroundColor =
                typedArray.getColor(R.styleable.ProgressLayout_progressLoadingStateBackgroundColor, Color.TRANSPARENT);

        emptyStateBackgroundColor =
                typedArray.getColor(R.styleable.ProgressLayout_progressEmptyStateBackgroundColor, Color.TRANSPARENT);

        errorStateBackgroundColor =
                typedArray.getColor(R.styleable.ProgressLayout_progressErrorStateBackgroundColor, Color.TRANSPARENT);

        typedArray.recycle();
    }

    public void showContent() {
        switchState(State.CONTENT, null);
    }

    public void showLoading() {
        switchState(State.LOADING, null);
    }

    String loadingText;

    public void showLoading(String loadText) {
        loadingText = loadText;
        switchState(State.LOADING_TEXT, null);
    }

    String emptyText;

    /**
     * 盖子页面中某个区域的空页面
     *
     * @param emptyText
     */
    public void showEmpty(String emptyText) {
        this.emptyText = emptyText;
        switchState(State.EMPTY, null);
    }

    /**
     * 显示最顶层的，盖全屏的空页面
     *
     * @param emptyText
     */
    public void showEmptyForParent(String emptyText) {
        this.emptyText = emptyText;
        switchState(State.EMPTY_PARENT, null);
    }


    public void showError(OnClickListener onClickListener) {
        switchState(State.ERROR, onClickListener);
    }

    String errorMessage, errorButton;


    public void showError(OnClickListener onClickListener, String errorMessage, String button) {
        this.errorMessage = errorMessage;
        this.errorButton = button;
        switchState(State.ERROR_SMALL, onClickListener);
    }


    /**
     * Get which state is set
     *
     * @return State
     */
    public State getState() {
        return state;
    }

    /**
     * Check if content is shown
     *
     * @return boolean
     */
    public boolean isContent() {
        return state == State.CONTENT;
    }

    /**
     * Check if loading state is shown
     *
     * @return boolean
     */
    public boolean isLoading() {
        return state == State.LOADING;
    }

    /**
     * Check if empty state is shown
     *
     * @return boolean
     */
    public boolean isEmpty() {
        return state == State.EMPTY;
    }

    /**
     * Check if error state is shown
     *
     * @return boolean
     */
    public boolean isError() {
        return state == State.ERROR;
    }

    private void switchState(State state, OnClickListener onClickListener) {
        this.state = state;
        switch (state) {
            case CONTENT:
                hideLoadingView();
                hideEmptyView();
                hideErrorView();
                break;
            case LOADING:
                hideEmptyView();
                hideErrorView();
                setLoadingView();
                break;
            case LOADING_TEXT:
                hideEmptyView();
                hideErrorView();
                setLoadingView(loadingText);
                break;
            case EMPTY:
                hideLoadingView();
                hideErrorView();
                setEmptyView(emptyText);
                break;
            case EMPTY_PARENT:
                hideLoadingView();
                hideErrorView();
                setEmptyViewForParent(emptyText);
                break;
            case ERROR:
                hideLoadingView();
                hideEmptyView();
                setErrorView(onClickListener);
                break;
        }
    }

    private void setLoadingView() {
        if (loadingStateRelativeLayout == null) {
            view = inflater.inflate(R.layout.progress_loading_view, null);
            loadingStateRelativeLayout = (RelativeLayout) view.findViewById(R.id.loadingStateRelativeLayout);

            loadingStateProgressBar = (DilatingDotsProgressBar) view.findViewById(R.id.loadingStateProgressBar);

            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
//            layoutParams.addRule(CENTER_IN_PARENT);

            if (cornerResId != -1) {
                loadingStateRelativeLayout.setBackgroundResource(cornerResId);
            }
            addView(loadingStateRelativeLayout, layoutParams);
        } else {
            loadingStateRelativeLayout.setVisibility(VISIBLE);
        }
        loadingStateProgressBar.showNow();

    }

    private void setLoadingView(String loadtext) {
        if (loadingStateRelativeLayout == null) {
            view = inflater.inflate(R.layout.progress_loading_view, null);
            loadingStateRelativeLayout = (RelativeLayout) view.findViewById(R.id.loadingStateRelativeLayout);

            loadingStateProgressBar = (DilatingDotsProgressBar) view.findViewById(R.id.loadingStateProgressBar);
            TextView text = (TextView) view.findViewById(R.id.loading_text);
            text.setText(loadtext);
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
//            layoutParams.addRule(CENTER_IN_PARENT);

            if (cornerResId != -1) {
                loadingStateRelativeLayout.setBackgroundResource(cornerResId);
            }

            addView(loadingStateRelativeLayout, layoutParams);
        } else {
            loadingStateRelativeLayout.setVisibility(VISIBLE);
        }
        loadingStateProgressBar.showNow();

    }

    /**
     * 设置空数据展示页面，该方法会盖住除了toolBar以外的整个页面
     */
    public void setEmptyViewForParent(String showText) {
//        ViewGroup contentContainer = (ViewGroup) findViewById(R.id.fragmentContainer);
        view = inflater.inflate(R.layout.progress_empty_custom_view, null);
        emptyStateRelativeLayout = (RelativeLayout) view.findViewById(R.id.emptyStateRelativeLayout);
        TextView textView = (TextView) view.findViewById(R.id.empty_textview);
        if (!TextUtils.isEmpty(showText)) {
            textView.setText(showText);
        }
        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(CENTER_IN_PARENT);
//        contentContainer.addView(view, layoutParams);
        addView(view, layoutParams);
    }

    /**
     * 空数据页面，为某个区域展示空页面使用
     *
     * @param showText
     */
    public void setEmptyView(String showText) {
        if (emptyStateRelativeLayout == null) {
            view = inflater.inflate(R.layout.progress_empty_custom_view, null);
            emptyStateRelativeLayout = (RelativeLayout) view.findViewById(R.id.emptyStateRelativeLayout);
            emptyIcon = (ImageView) view.findViewById(R.id.emptyIcon);
            TextView textView = (TextView) view.findViewById(R.id.empty_textview);
            if (!TextUtils.isEmpty(showText)) {
                textView.setText(showText);
            }
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(CENTER_IN_PARENT);
            addView(view, layoutParams);
        } else {
            emptyStateRelativeLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置空页面
     */
    /*public void setSmallEmptyIcon() {
        if (emptyIcon != null) {
            ViewGroup.LayoutParams params = emptyIcon.getLayoutParams();
            int size = (int) DisplayUtil.dp2px(getContext(), 40);
            params.width = size;
            params.height = size;
            emptyIcon.requestLayout();
        }
    }*/
    private void setErrorView(OnClickListener onClickListener) {
        if (errorStateRelativeLayout == null) {
            ViewGroup contentContainer = null;
//            ViewGroup contentContainer = (ViewGroup) findViewById(R.id.fragmentContainer);
            view = inflater.inflate(R.layout.progress_error_view, null);
            errorStateRelativeLayout = (RelativeLayout) view.findViewById(R.id.errorStateRelativeLayout);
            errorStateButton = (Button) view.findViewById(R.id.errorStateButton);
            if (onClickListener != null) {
                errorStateButton.setOnClickListener(onClickListener);
            }

            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(CENTER_IN_PARENT);

            if (cornerResId != -1) {
                errorStateRelativeLayout.setBackgroundResource(cornerResId);
            }

            if (contentContainer != null) {
                contentContainer.addView(view, layoutParams);
            } else {
                addView(view, layoutParams);
            }
        } else {
            errorStateRelativeLayout.setVisibility(VISIBLE);
        }
    }

    private void hideLoadingView() {
        if (loadingStateRelativeLayout != null) {
            loadingStateRelativeLayout.setVisibility(GONE);
            loadingStateProgressBar.hideNow();
        }
    }

    private void hideEmptyView() {
        if (emptyStateRelativeLayout != null) {
            emptyStateRelativeLayout.setVisibility(GONE);
        }
    }

    private void hideErrorView() {
        if (errorStateRelativeLayout != null) {
            errorStateRelativeLayout.setVisibility(GONE);
        }
    }

    public enum State {
        CONTENT, LOADING, EMPTY, EMPTY_PARENT, ERROR, ERROR_SMALL, LOADING_TEXT
    }

}