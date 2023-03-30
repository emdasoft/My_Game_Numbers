package com.emdasoft.mygamenumbers.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.emdasoft.mygamenumbers.R
import com.emdasoft.mygamenumbers.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private val args by navArgs<ResultFragmentArgs>()

    private var _binding: FragmentResultBinding? = null
    private val binding: FragmentResultBinding
        get() = _binding ?: throw RuntimeException("FragmentResultBinding = null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
        with(binding) {
            imageSmile.setImageResource(getSmileResId())
            tvRequiredAnswer.text = String.format(
                getString(R.string.tv_required_answer),
                args.result.gameSettings.minCountOfRightAnswers
            )
            tvScoreAnswer.text =
                String.format(getString(R.string.tv_score_answer), args.result.countOfRightAnswers)
            tvRequiredPercent.text = String.format(
                getString(R.string.tv_required_percent),
                args.result.gameSettings.minPercentOfRightAnswers
            )
            tvScorePercent.text = String.format(
                getString(R.string.tv_score_percent), getPercentOfRightAnswers()
            )
        }
    }

    private fun getSmileResId(): Int {
        return if (args.result.winner) {
            R.drawable.fun_smile
        } else {
            R.drawable.sad_smile
        }
    }

    private fun getPercentOfRightAnswers() = with(args.result) {
        if (countOfQuestions == 0) {
            0
        } else {
            ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
        }
    }

    private fun setOnClickListeners() {
        binding.retryButton.setOnClickListener {
            retryGame()
        }
    }

    private fun retryGame() {
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}