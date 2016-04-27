
可用来在切换Fragment时提供切换动画，只需在Fragment的onCreateAnimation()方法中调用：

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return CubeAnimation.create(CubeAnimation.UP, enter, DURATION);
    }

详情可查看GitHub:https://github.com/kakajika/FragmentAnimations